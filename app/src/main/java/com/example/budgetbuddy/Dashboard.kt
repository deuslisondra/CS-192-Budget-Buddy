package com.example.budgetbuddy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import java.lang.Float.parseFloat

class Dashboard : AppCompatActivity() {

    private lateinit var dbRef : DatabaseReference
    private lateinit var dbList: ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)

        val btnTransactions = findViewById<Button>(R.id.btnTransactions) as Button
        btnTransactions.setOnClickListener {
            val intentTransactions = Intent(this, Transactions::class.java)
            startActivity(intentTransactions)
        }

        val btnWallets = findViewById<Button>(R.id.btnWallets) as Button
        btnWallets.setOnClickListener {
            val intentWallets = Intent(this, Wallets::class.java)
            startActivity(intentWallets)
        }

        val btnProfile = findViewById<Button>(R.id.btnProfile) as Button
        btnProfile.setOnClickListener {
            val intentProfile = Intent(this, Profile::class.java)
            startActivity(intentProfile)
        }

        val btnSettings = findViewById<Button>(R.id.btnSettings) as Button
        btnSettings.setOnClickListener {
            val intentSettings = Intent(this, Settings::class.java)
            startActivity(intentSettings)
        }

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val userName = sharedPreferences.getString("username", "")

        val balanceDisplay = findViewById<TextView>(R.id.textBalance) as TextView


        dbRef = FirebaseDatabase.getInstance().getReference("Users/$userName/Wallets")

        dbRef.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var totalCount = 0.00f

                for (child in snapshot.children) {
                    val x = child.child("addWalletAmt")
                    val y = parseFloat(x.value as String)

                    totalCount += y

                    //Log.i("TAG", "$y")
                }
                totalCount.toString()
                balanceDisplay.text = "$totalCount"
                //Log.i("totalCount", "$totalCount")
            }
        })

    }

}
