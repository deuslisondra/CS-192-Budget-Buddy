package com.example.budgetbuddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.settings)

        val btnSaveSettings = findViewById<Button>(R.id.btnSaveSettings) as Button
        btnSaveSettings.setOnClickListener {
            val intentDashboard = Intent(this, Dashboard::class.java)
            startActivity(intentDashboard)
        }

    }
}