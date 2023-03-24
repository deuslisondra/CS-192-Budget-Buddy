package com.example.budgetbuddy

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Wallets : AppCompatActivity() {
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var editBtn: ImageView
    private lateinit var dltBtn: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.wallet_layout)
        addsBtn = findViewById(R.id.addWalletButton)
        addsBtn.setOnClickListener { addInfo() }

        editBtn = findViewById(R.id.walletEdit)
        editBtn.setOnClickListener { editInfo() }

        dltBtn = findViewById(R.id.walletDelete)
        dltBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to delete wallet?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, _->
                    Toast.makeText(this,"Deleting Wallet Information Success",Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()}

    }

    private fun addInfo() {
        val popUpWallet = LayoutInflater.from(this)
        val v = popUpWallet.inflate(R.layout.add_wallet,null)
        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(v)
        addDialog.setPositiveButton("Ok"){
                dialog,_->
                Toast.makeText(this,"Adding Wallet Information Success",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
                dialog,_->
                dialog.dismiss()
                Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
        }
        addDialog.create()
        addDialog.show()
    }
    private fun editInfo() {
        val popUpWallet = LayoutInflater.from(this)
        val v = popUpWallet.inflate(R.layout.edit_wallet,null)
        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(v)
        addDialog.setPositiveButton("Ok"){
                dialog,_->
            Toast.makeText(this,"Editing Wallet Information Success",Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
                dialog,_->
            dialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
        }
        addDialog.create()
        addDialog.show()
    }

}