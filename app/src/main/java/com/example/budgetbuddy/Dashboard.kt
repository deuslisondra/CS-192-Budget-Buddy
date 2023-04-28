package com.example.budgetbuddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class Dashboard : AppCompatActivity() {

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

        val btnProfile = findViewById<Button>(R.id.btnProfile) as Button
        btnProfile.setOnClickListener {
            val intentProfile = Intent(this, Profile::class.java)
            startActivity(intentProfile)
        }

        val btnSettings = findViewById<Button>(R.id.btnSettings) as Button
        btnSettings.setOnClickListener {
            val intentSettings = Intent(this, Settings::class.java)
            startActivity(intentSettings)
        }
    }
}
