package com.example.budgetbuddy

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.adapters.TrnsctnAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Transactions : AppCompatActivity() {
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var editBtn: ImageView
    private lateinit var dltBtn: ImageView

    private lateinit var trnsctnRecyclerView: RecyclerView
    private lateinit var trnsctnList: ArrayList<TransactionModel>

    private lateinit var trnsctnAdapter:TrnsctnAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transaction_layout)

        trnsctnList = ArrayList()
        addsBtn = findViewById(R.id.addTransactionButton)
        trnsctnRecyclerView = findViewById(R.id.rvTrnsctn)
        trnsctnRecyclerView.layoutManager = LinearLayoutManager(this)
        trnsctnRecyclerView.adapter = trnsctnAdapter
        trnsctnAdapter = TrnsctnAdapter(trnsctnList)
        addsBtn.setOnClickListener{ addInfo() }
    }

    private fun addInfo() {
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.add_transaction, null)
        val transactionName = v.findViewById<EditText>(R.id.transactionName)
        val transactionBalance = v.findViewById<EditText>(R.id.transactionBalance)
        val transactionType = v.findViewById<EditText>(R.id.transactionType)
        val walletName = v.findViewById<EditText>(R.id.walletName)
        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(v)
        addDialog.setPositiveButton("Ok") {
            dialog,_->
            val names = transactionName.text.toString()
            val balances = transactionBalance.text.toString()
            val types = transactionType.text.toString()
            val wallets = walletName.text.toString()
            trnsctnList.add(
                TransactionModel("Transaction name: $names",
                "Balance: $balances", "Type: $types", "Wallet name: $wallets"))
            trnsctnAdapter.notifyDataSetChanged()
            Toast.makeText(this, "Adding user information success",Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel") {
            dialog,_->
            dialog.dismiss()
            Toast.makeText(this, "Cancel",Toast.LENGTH_SHORT).show()
        }
        addDialog.create()
        addDialog.show()
    }


}