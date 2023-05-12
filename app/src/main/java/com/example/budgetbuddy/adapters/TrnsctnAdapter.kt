package com.example.budgetbuddy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.R
import com.example.budgetbuddy.TransactionModel

class TrnsctnAdapter(private val trnsctnList: ArrayList<TransactionModel>) :
    RecyclerView.Adapter<TrnsctnAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrnsctnAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.transactions, parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTrnsctn = trnsctnList[position]
        holder.tvTrnsctnName.text = currentTrnsctn.addTransactionName
        holder.tvTrnsctnAmount.text = currentTrnsctn.addTransactionBalance
        holder.tvTrnsctnWallet.text = currentTrnsctn.addTransactionWallet
        holder.tvTrnsctnType.text = currentTrnsctn.addTransactionType
    }

    override fun getItemCount(): Int {
        return trnsctnList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvTrnsctnName : TextView = itemView.findViewById(R.id.mTitle)
        val tvTrnsctnAmount : TextView = itemView.findViewById(R.id.mSubTitleAmount)
        val tvTrnsctnWallet : TextView = itemView.findViewById(R.id.mSubTitleWallet)
        val tvTrnsctnType : TextView = itemView.findViewById(R.id.mSubTitleType)
    }
}