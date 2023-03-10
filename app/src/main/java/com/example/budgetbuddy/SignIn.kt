package com.example.budgetbuddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

class SignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in)

        val btnSignIn = findViewById<Button>(R.id.btnSignIn) as Button
        btnSignIn.setOnClickListener {

            val textUsername: TextView = findViewById<TextView>(R.id.textUsername)
            val username: String = textUsername.text.toString()

            val textPassword: TextView = findViewById<TextView>(R.id.textPassword)
            val password: String = textPassword.text.toString()

            if (username == "username" && password == "password") {
                Toast.makeText(this,"Signed in successfully", Toast.LENGTH_SHORT).show()
                val intentDashboard = Intent(this, Dashboard::class.java)
                startActivity(intentDashboard)
            }
            else {
                Toast.makeText(this,"Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }

        val btnSignUp = findViewById<TextView>(R.id.textSignUp) as TextView
        btnSignUp.setOnClickListener {
            val intentSignUp = Intent(this, SignUp::class.java)
            startActivity(intentSignUp)
        }
    }
}