package com.example.budgetbuddy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class SignIn : AppCompatActivity() {
    private lateinit var textUsername: EditText
    private lateinit var textPassword: EditText

    private lateinit var btnSignIn: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in)
        textUsername = findViewById(R.id.textUsername)
        textPassword = findViewById(R.id.textPassword)
        btnSignIn = findViewById(R.id.btnSignIn)

        btnSignIn.setOnClickListener {
            checkUser()
        }

        val btnSignUp = findViewById<TextView>(R.id.textSignUp) as TextView
        btnSignUp.setOnClickListener {
            val intentSignUp = Intent(this, SignUp::class.java)
            startActivity(intentSignUp)
        }
    }
    private fun checkUser(){
        Log.d("UserData", "Check")
        val userName: String = textUsername.text.toString()
        val password: String = textPassword.text.toString()
        dbRef = FirebaseDatabase.getInstance().getReference("Users")
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.hasChild(userName)){
                    val userPassword = snapshot.child(userName).child("userPassword").getValue(String::class.java)
                    if(userPassword==password){
                        val intentDashboard = Intent(applicationContext, Dashboard::class.java)
                        startActivity(intentDashboard)
                    }
                    else{
                        Toast.makeText(applicationContext, "Username and password does not match!", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(applicationContext, "Username does not exist", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}