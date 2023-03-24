package com.example.budgetbuddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

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

        val btnSignIn = findViewById<TextView>(R.id.textSignIn)
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

        if(userName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || fullName.isEmpty() || emailAddress.isEmpty() || date.isEmpty()){
            Toast.makeText(this, "Please fill all the fields.", Toast.LENGTH_SHORT).show()
        }
        else if(password!=confirmPassword){
            Toast.makeText(this, "Password does not match.", Toast.LENGTH_SHORT).show()
        }
        else{
            dbRef.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.hasChild(userName)){
                        Toast.makeText(applicationContext, "Username already exists", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        val user = UserModel(userName, password, fullName, emailAddress, date)
                        dbRef.child(userName).setValue(user)
                            .addOnCompleteListener {
                                val intentSignIn = Intent(applicationContext, SignIn::class.java)
                                startActivity(intentSignIn)
                            }.addOnFailureListener {err ->
                                Toast.makeText(applicationContext, "Error ${err.message}", Toast.LENGTH_LONG).show()
                            }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        }

    }
}