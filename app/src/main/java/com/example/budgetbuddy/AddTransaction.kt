package com.example.budgetbuddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddTransaction : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_transaction)

        val btnConfirm = findViewById<Button>(R.id.btnConfirm) as Button
        btnConfirm.setOnClickListener {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT)
            val intentTransactions = Intent(this, Transactions::class.java)
            startActivity(intentTransactions)
        }

        val btnCancel = findViewById<Button>(R.id.btnCancel) as Button
        btnCancel.setOnClickListener {
            Toast.makeText(this, "Operation cancelled", Toast.LENGTH_SHORT)
            val intentTransactions = Intent(this, Transactions::class.java)
            startActivity(intentTransactions)
        }
    }
}