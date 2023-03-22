package com.example.budgetbuddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSignIn = findViewById<Button>(R.id.btnSignIn) as Button
        btnSignIn.setOnClickListener {
            val intentSignIn = Intent(this, SignIn::class.java)
            startActivity(intentSignIn)
        }

        val btnSignUp = findViewById<Button>(R.id.btnSignUp) as Button
        btnSignUp.setOnClickListener{
            val intentSignUp = Intent(this, SignUp::class.java)
            startActivity(intentSignUp)
        }

    }
}