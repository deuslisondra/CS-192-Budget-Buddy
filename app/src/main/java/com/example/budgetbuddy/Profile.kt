package com.example.budgetbuddy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.budgetbuddy.adapters.WltAdapter
import com.google.firebase.database.*

class Profile : AppCompatActivity() {
    private lateinit var dbRef : DatabaseReference

    private lateinit var textUsername : TextView
    private lateinit var textPassword : TextView
    private lateinit var textFullName: TextView
    private lateinit var textEmailAddress: TextView
    private lateinit var textDate: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)
        Toast.makeText(applicationContext, "Profile", Toast.LENGTH_SHORT).show()
        textUsername = findViewById(R.id.editTextUserName)
        textPassword = findViewById(R.id.editTextPassword)
        textFullName = findViewById(R.id.editTextName)
        textEmailAddress = findViewById(R.id.editTextEmail)
        textDate = findViewById(R.id.editTextBday)

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val userName = sharedPreferences.getString("username", "") as String
        setUserProfile(userName)
        val btnEditProfile = findViewById<Button>(R.id.btnEditProfile) as Button
        btnEditProfile.setOnClickListener {
            val intentEditProfile = Intent(this, EditProfile::class.java)
            startActivity(intentEditProfile)
        }
    }
    private fun setUserProfile(username: String){
        dbRef = FirebaseDatabase.getInstance().getReference("Users/$username")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot){

                textUsername.text = snapshot.child("userName").getValue(String::class.java)
                textPassword.text = snapshot.child("userPassword").getValue(String::class.java)
                textFullName.text = snapshot.child("userFullname").getValue(String::class.java)
                textEmailAddress.text = snapshot.child("userEmailAddress").getValue(String::class.java)
                textDate.text = snapshot.child("userDate").getValue(String::class.java)

            }

            override fun onCancelled(error: DatabaseError){
                TODO()
            }
        })
    }
}