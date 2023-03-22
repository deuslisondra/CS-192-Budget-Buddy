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
    private var match : Boolean = false
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
            if(match){
                Toast.makeText(this,"Signed in successfully", Toast.LENGTH_SHORT).show()
                val intentDashboard = Intent(this, Dashboard::class.java)
                startActivity(intentDashboard)
            }
            else{
                Toast.makeText(this,"Invalid username or password", Toast.LENGTH_SHORT).show()
            }
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
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnap in snapshot.children) {
                        val userData = userSnap.getValue(UserModel::class.java)
                        Log.d("UserName", userData!!.userName.toString())
                        Log.d("UserPassword", userData.userPassword.toString())

                        if (userData.userName.toString() == userName && userData.userPassword.toString() == password) {
                            match = true
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}