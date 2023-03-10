package com.example.budgetbuddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)

        val btnSignUp = findViewById<Button>(R.id.btnSignUp) as Button
        btnSignUp.setOnClickListener {
            val intentDashboard = Intent(this, Dashboard::class.java)
            startActivity(intentDashboard)
        }

        val btnSignIn = findViewById<TextView>(R.id.textSignIn) as TextView
        btnSignIn.setOnClickListener {
            val intentSignIn = Intent(this, SignIn::class.java)
            startActivity(intentSignIn)
        }
    }
}