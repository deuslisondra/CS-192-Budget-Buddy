package com.example.budgetbuddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Transactions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.transactions)

        val btnUpdate = findViewById<Button>(R.id.btnUpdate) as Button
        btnUpdate.setOnClickListener {
            val intentUpdateTransaction = Intent(this, UpdateTransaction::class.java)
            startActivity(intentUpdateTransaction)
        }

        val btnAdd = findViewById<Button>(R.id.btnAdd) as Button
        btnAdd.setOnClickListener {
            val intentAddTransaction = Intent(this, AddTransaction::class.java)
            startActivity(intentAddTransaction)
        }
    }
}