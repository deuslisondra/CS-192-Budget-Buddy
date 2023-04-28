package com.example.budgetbuddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.profile)

        val btnSaveProfile = findViewById<Button>(R.id.btnSaveProfile) as Button
        btnSaveProfile.setOnClickListener {
            val intentDashboard = Intent(this, Dashboard::class.java)
            startActivity(intentDashboard)
        }
    }
}