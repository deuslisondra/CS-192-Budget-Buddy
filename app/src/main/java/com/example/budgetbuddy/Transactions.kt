package com.example.budgetbuddy

import android.annotation.SuppressLint
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
import com.example.budgetbuddy.adapters.TrnsctnAdapter
import com.example.budgetbuddy.adapters.WltAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class Transactions : AppCompatActivity() {
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var editBtn: ImageView
    private lateinit var dltBtn: ImageView

    private lateinit var trnsctnRecyclerView: RecyclerView
    private lateinit var trnsctnLoadingData: TextView
    private lateinit var trnsctnList: ArrayList<TransactionModel>
    private lateinit var dbRef : DatabaseReference

    private lateinit var trnsctnType : EditText
    private lateinit var trnsctnName : EditText
    private lateinit var trnsctnWallet : EditText
    private lateinit var trnsctnAmt : EditText
    private lateinit var trnsctnOk : Button
    private lateinit var trnsctnCncl : Button
    private lateinit var dbRef2 : DatabaseReference

    private lateinit var trnsctnAdapter:TrnsctnAdapter
    private lateinit var dialog: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transaction_layout)

        trnsctnList = ArrayList()
        addsBtn = findViewById(R.id.addTransactionButton)
        trnsctnRecyclerView = findViewById(R.id.rvTrnsctn)
        trnsctnRecyclerView.layoutManager = LinearLayoutManager(this)
        val trnsctnAdapter = TrnsctnAdapter(trnsctnList)
        trnsctnRecyclerView.adapter = trnsctnAdapter

        getTransactionsData()

        addsBtn.setOnClickListener{ addInfo() }
    }

    private fun addInfo() {
        val trnsctnAdapter = TrnsctnAdapter(trnsctnList)
        trnsctnRecyclerView.adapter = trnsctnAdapter

        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.add_transaction, null)

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val userName = sharedPreferences.getString("username", "")

        val transactionName = v.findViewById<EditText>(R.id.transactionName)
        val transactionBalance = v.findViewById<EditText>(R.id.transactionBalance)
        val transactionType = v.findViewById<EditText>(R.id.transactionType)
        val walletName = v.findViewById<EditText>(R.id.walletName)
        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)

        addDialog.setPositiveButton("Ok") { dialog, _ ->
            val names = transactionName.text.toString()
            val balances = transactionBalance.text.toString()
            val types = transactionType.text.toString()
            val wallets = walletName.text.toString()

            val dbRef2 = FirebaseDatabase.getInstance().getReference("Users/$userName/Transactions")
            val transactionId = dbRef2.push().key!!

            val newTransaction = TransactionModel(
                transactionId,
                "Transaction name: $names",
                "Amount: $balances",
                "Type: $types",
                "Wallet name: $wallets"
            )

            dbRef2.child(transactionId).setValue(newTransaction)
                .addOnCompleteListener {
                    trnsctnAdapter.notifyDataSetChanged()
                    Toast.makeText(this, "Adding user information success", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
        }

        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
        }

        addDialog.create()
        addDialog.show()
    }

    private fun editInfo(transaction: TransactionModel) {
        val popUpTransaction = LayoutInflater.from(this)
        val v = popUpTransaction.inflate(R.layout.edit_transaction, null)

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val userName = sharedPreferences.getString("username", "")

        trnsctnName = v.findViewById<EditText>(R.id.transactionName)
        trnsctnAmt = v.findViewById<EditText>(R.id.transactionBalance)
        trnsctnWallet = v.findViewById<EditText>(R.id.walletName)
        trnsctnType = v.findViewById<EditText>(R.id.transactionType)
        trnsctnOk = v.findViewById<Button>(R.id.btnOk)
        trnsctnCncl = v.findViewById<Button>(R.id.btnCancel)

        // Pre-populate the fields with the current transaction data
        trnsctnName.setText(transaction.addTransactionName)
        trnsctnAmt.setText(transaction.addTransactionBalance)
        trnsctnWallet.setText(transaction.addTransactionWallet)
        trnsctnType.setText(transaction.addTransactionType)

        dbRef2 = FirebaseDatabase.getInstance().getReference("Users/$userName/Transactions").child(transaction.trnsctnId!!)
        val editDialog = AlertDialog.Builder(this)

        trnsctnOk.setOnClickListener {
            updateTransactionData(transaction)
        }

        editDialog.setView(v)
        dialog = editDialog.create()
        dialog.show()
    }

    private fun deleteInfo(transaction: TransactionModel) {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val userName = sharedPreferences.getString("username", "")

        dbRef2 = FirebaseDatabase.getInstance().getReference("Users/$userName/Transactions").child(transaction.trnsctnId!!)

        val mTask = dbRef2.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Transaction data deleted", Toast.LENGTH_LONG).show()
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }
    private fun updateTransactionData(transaction: TransactionModel) {
        val updatedTransactionName = trnsctnName.text.toString()
        val updatedTransactionAmt = trnsctnAmt.text.toString()
        val updatedTransactionWallet = trnsctnWallet.text.toString()
        val updatedTransactionType = trnsctnType.text.toString()

        if (updatedTransactionName.isEmpty()) {
            trnsctnName.error = "Please enter transaction name"
            return
        }
        if (updatedTransactionAmt.isEmpty()) {
            trnsctnAmt.error = "Please enter transaction amount"
            return
        }
        if (updatedTransactionWallet.isEmpty()) {
            trnsctnWallet.error = "Please enter transaction wallet"
            return
        }
        if (updatedTransactionType.isEmpty()) {
            trnsctnType.error = "Please enter transaction type"
            return
        }

        val updates = mapOf<String, Any>(
            "addTransactionName" to updatedTransactionName,
            "addTransactionBalance" to updatedTransactionAmt,
            "addTransactionWallet" to updatedTransactionWallet,
            "addTransactionType" to updatedTransactionType
        )

        dbRef2.updateChildren(updates)
            .addOnCompleteListener {
                Toast.makeText(this, "Transaction updated successfully", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun getTransactionsData() {
        // Hide the RecyclerView and show the loading indicator
        //trnsctnRecyclerView.visibility = View.GONE
        //trnsctnLoadingData.visibility = View.VISIBLE

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val userName = sharedPreferences.getString("username", "")

        dbRef = FirebaseDatabase.getInstance().getReference("Users/$userName/Transactions")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trnsctnList.clear()
                if (snapshot.exists()) {
                    for (trnsctnSnap in snapshot.children) {
                        val trnsctnData = trnsctnSnap.getValue(TransactionModel::class.java)
                        trnsctnData?.let {
                            trnsctnList.add(it)
                        }
                    }

                    // Create and set the adapter for the RecyclerView
                    val trnsctnAdapter = TrnsctnAdapter(trnsctnList)
                    trnsctnRecyclerView.adapter = trnsctnAdapter

                    trnsctnAdapter.setOnItemClickListener(object : TrnsctnAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int, isEdit: Boolean) {
                            val selectedTransaction = trnsctnList[position]
                            if (isEdit) {
                                Log.d("TransactionAdapter", "Edit button clicked for position $position")
                                editInfo(selectedTransaction)
                            } else {
                                Log.d("TransactionAdapter", "Delete button clicked for position $position")
                                deleteInfo(selectedTransaction)
                            }
                        }
                    })

                    // Show the RecyclerView and hide the loading indicator
                    trnsctnRecyclerView.visibility = View.VISIBLE
                  //  trnsctnLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event if needed
            }
        })
    }


}