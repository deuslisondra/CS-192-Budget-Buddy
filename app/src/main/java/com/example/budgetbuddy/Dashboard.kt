package com.example.budgetbuddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class Dashboard : AppCompatActivity() {

    private lateinit var btnTransactions: Button
    private lateinit var btnWallets: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)

        val btnTransactions = findViewById<Button>(R.id.btnTransactions) as Button
        btnTransactions.setOnClickListener {
            val intentTransactions = Intent(this, Transactions::class.java)
            startActivity(intentTransactions)
        }

        val btnWallets = findViewById<Button>(R.id.btnWallets) as Button
        btnWallets.setOnClickListener {
            val intentWallets = Intent(this, Wallets::class.java)
            startActivity(intentWallets)
        }

    }
}
