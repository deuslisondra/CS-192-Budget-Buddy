package com.example.budgetbuddy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.R
import com.example.budgetbuddy.WalletModel

class WltAdapter(private val wltList: ArrayList<WalletModel>) :
    RecyclerView.Adapter<WltAdapter.ViewHolder>(){

    private lateinit var wListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int, isEdit: Boolean)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        wListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WltAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.wallet_list, parent,false)
        val editButton = itemView.findViewById<ImageView>(R.id.walletEdit)
        val deleteButton = itemView.findViewById<ImageView>(R.id.walletDelete)
        return ViewHolder(itemView, editButton, deleteButton, wListener)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentWlt = wltList[position]
        holder.tvWltName.text = currentWlt.addWalletType
        holder.tvWltAmount.text = currentWlt.addWalletAmt
    }

    override fun getItemCount(): Int {
        return wltList.size
    }

    class ViewHolder(itemView: View, editButton: ImageView, deleteButton: ImageView, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val tvWltName : TextView = itemView.findViewById(R.id.mTitle)
        val tvWltAmount: TextView = itemView.findViewById(R.id.mSubTitle)
        val editButton: ImageView = editButton
        val deleteButton: ImageView = deleteButton

        init {
            editButton.setOnClickListener {
                clickListener.onItemClick(adapterPosition, true)
            }

            deleteButton.setOnClickListener {
                clickListener.onItemClick(adapterPosition, false)
            }
        }
    }
}