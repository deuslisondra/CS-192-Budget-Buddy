package com.example.budgetbuddy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.R
import com.example.budgetbuddy.WalletModel

class WltAdapter(private val wltList: ArrayList<WalletModel>) :
    RecyclerView.Adapter<WltAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WltAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.wallet_list, parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentWlt = wltList[position]
        holder.tvWltName.text = currentWlt.addWalletType
    }

    override fun getItemCount(): Int {
        return wltList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvWltName : TextView = itemView.findViewById(R.id.mTitle)
    }
}