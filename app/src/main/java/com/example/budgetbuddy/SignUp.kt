package com.example.budgetbuddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
class SignUp : AppCompatActivity() {
    private lateinit var textUsername : EditText
    private lateinit var textPassword : EditText
    private lateinit var textConfirmPassword: EditText
    private lateinit var textFullName: EditText
    private lateinit var textEmailAddress: EditText
    private lateinit var textDate: EditText

    private lateinit var btnSignUp : Button

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)


        textUsername = findViewById(R.id.textUsername)
        textPassword = findViewById(R.id.textPassword)
        textConfirmPassword = findViewById(R.id.textConfirmPassword)
        textFullName = findViewById(R.id.textFullName)
        textEmailAddress = findViewById(R.id.textEmailAddress)
        textDate = findViewById(R.id.textDate)

        btnSignUp = findViewById(R.id.btnSignUp)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        btnSignUp.setOnClickListener {
            saveUserData()
        }

        val btnSignIn = findViewById<TextView>(R.id.textSignIn) as TextView
        btnSignIn.setOnClickListener {
            val intentSignIn = Intent(this, SignIn::class.java)
            startActivity(intentSignIn)
        }
    }
    private fun saveUserData(){
        val userName = textUsername.text.toString()
        val password = textPassword.text.toString()
        val confirmPassword = textConfirmPassword.text.toString()
        val fullName = textFullName.text.toString()
        val emailAddress = textEmailAddress.text.toString()
        val date = textDate.text.toString()

        if(userName.isEmpty()){
            textUsername.error = "Please enter username"
        }
        if(password.isEmpty()){
            textPassword.error = "Please enter password"
        }
        if(confirmPassword.isEmpty()){
            textConfirmPassword.error = "Please confirm password"
        }
        if(fullName.isEmpty()){
            textFullName.error = "Please enter full name"
        }
        if(emailAddress.isEmpty()){
            textEmailAddress.error = "Please enter email address"
        }
        if(date.isEmpty()){
            textDate.error = "Please enter birthday"
        }
        if(password!=confirmPassword){
            textPassword.error = "Password does not match"
            textConfirmPassword.error = "Confirm does not match"
        }
        val userID = dbRef.push().key!!

        val user = UserModel(userID, userName, password, fullName, emailAddress, date)
        dbRef.child(userID).setValue(user)
            .addOnCompleteListener {
                Toast.makeText(this,"User successfully added!", Toast.LENGTH_LONG).show()
                val intentSignIn = Intent(this, SignIn::class.java)
                startActivity(intentSignIn)
            }.addOnFailureListener {err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
        }
    }
}