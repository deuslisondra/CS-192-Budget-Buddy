package com.example.budgetbuddy

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.adapters.WltAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class Wallets : AppCompatActivity() {
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var editBtn: ImageView
    private lateinit var dltBtn: ImageView

    private lateinit var wltRecyclerView: RecyclerView
    private lateinit var wltLoadingData: TextView
    private lateinit var wltList: ArrayList<WalletModel>
    private lateinit var dbRef : DatabaseReference

    private lateinit var wltType : EditText
    private lateinit var wltAmt : EditText
    private lateinit var wltOk : Button
    private lateinit var wltCncl : Button
    private lateinit var dbRef2 : DatabaseReference

    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.wallet_fetch)
        wltRecyclerView = findViewById(R.id.rvWlt)
        wltRecyclerView.layoutManager = LinearLayoutManager(this)
        wltRecyclerView.setHasFixedSize(true)
        wltLoadingData = findViewById(R.id.wltLoadingData)
        wltList = arrayListOf<WalletModel>()

        getEmployeesData()

        addsBtn = findViewById(R.id.addWalletButton)
        addsBtn.setOnClickListener { addInfo() }
    }

    private fun addInfo() {
        val popUpWallet = LayoutInflater.from(this)
        val v = popUpWallet.inflate(R.layout.add_wallet,null)

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val userName = sharedPreferences.getString("username", "")

        wltType = v.findViewById<EditText>(R.id.walletType)
        wltAmt = v.findViewById<EditText>(R.id.walletAmount)
        wltOk = v.findViewById<Button>(R.id.btnOk)
        wltCncl = v.findViewById<Button>(R.id.btnCancel)
        dbRef2 = FirebaseDatabase.getInstance().getReference("Users/$userName/Wallets")
        val addDialog = AlertDialog.Builder(this)


        wltOk.setOnClickListener{
            saveWalletData()
        }

        addDialog.setView(v)
        dialog = addDialog.create()
        dialog.show()
    }
    private fun editInfo(wallet: WalletModel) {
        val popUpWallet = LayoutInflater.from(this)
        val v = popUpWallet.inflate(R.layout.edit_wallet,null)

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val userName = sharedPreferences.getString("username", "")

        wltType = v.findViewById<EditText>(R.id.walletType)
        wltAmt = v.findViewById<EditText>(R.id.walletAmount)
        wltOk = v.findViewById<Button>(R.id.btnOk)
        wltCncl = v.findViewById<Button>(R.id.btnCancel)

        // Pre-populate the fields with the current wallet data
        wltType.setText(wallet.addWalletType)
        wltAmt.setText(wallet.addWalletAmt)

        dbRef2 = FirebaseDatabase.getInstance().getReference("Users/$userName/Wallets").child(wallet.wltId!!)
        val editDialog = AlertDialog.Builder(this)

        wltOk.setOnClickListener{
            updateWalletData(wallet)
        }

        editDialog.setView(v)
        dialog = editDialog.create()
        dialog.show()
    }

    private fun deleteInfo(wallet: WalletModel) {

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val userName = sharedPreferences.getString("username", "")

        dbRef2 = FirebaseDatabase.getInstance().getReference("Users/$userName/Wallets").child(wallet.wltId!!)

        val mTask = dbRef2.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Wallet data deleted", Toast.LENGTH_LONG).show()

        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun saveWalletData() {
        val addWalletType = wltType.text.toString()
        val addWalletAmt = wltAmt.text.toString()

        if (addWalletType.isEmpty()) {
            wltType.error = "Please enter wallet type"
            return
        }
        if (addWalletAmt.isEmpty()) {
            wltAmt.error = "Please enter wallet amount"
            return
        }

        val wltId = dbRef2.push().key!!
        val newWallet = WalletModel(wltId, addWalletType, addWalletAmt)

        dbRef2.child(wltId).setValue(newWallet)
            .addOnCompleteListener {
                Toast.makeText(this, "Wallet added successfully", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun updateWalletData(wallet: WalletModel) {
        val updatedWalletType = wltType.text.toString()
        val updatedWalletAmt = wltAmt.text.toString()

        if (updatedWalletType.isEmpty()) {
            wltType.error = "Please enter wallet type"
            return
        }
        if (updatedWalletAmt.isEmpty()) {
            wltAmt.error = "Please enter wallet amount"
            return
        }

        val updates = mapOf<String, Any>(
            "addWalletType" to updatedWalletType,
            "addWalletAmt" to updatedWalletAmt
        )

        dbRef2.updateChildren(updates)
            .addOnCompleteListener {
                Toast.makeText(this, "Wallet updated successfully", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun getEmployeesData(){
        wltRecyclerView.visibility = View.GONE
        wltLoadingData.visibility = View.VISIBLE

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val userName = sharedPreferences.getString("username", "")

        dbRef = FirebaseDatabase.getInstance().getReference("Users/$userName/Wallets")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot){
                wltList.clear()
                if(snapshot.exists()){
                    for(wltSnap in snapshot.children){
                        val wltData = wltSnap.getValue(WalletModel::class.java)
                        wltList.add(wltData!!)
                    }
                    val wAdapter = WltAdapter(wltList)
                    wltRecyclerView.adapter = wAdapter

                    wAdapter.setOnItemClickListener(object: WltAdapter.onItemClickListener{
                        override fun onItemClick(position: Int, isEdit: Boolean) {
                            val selectedWallet = wltList[position]
                            if (isEdit) {
                                Log.d("WltAdapter", "Edit button clicked for position $position")
                                editInfo(selectedWallet)
                            } else {
                                Log.d("WltAdapter", "Delete button clicked for position $position")
                                deleteInfo(selectedWallet)
                            }
                        }
                    })

                    wltRecyclerView.visibility = View.VISIBLE
                    wltLoadingData.visibility = View.GONE
                }
            }
            override fun onCancelled(error: DatabaseError){
                TODO()
            }
        })

    }
}