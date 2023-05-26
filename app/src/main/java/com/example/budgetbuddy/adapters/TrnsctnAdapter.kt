package com.example.budgetbuddy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetbuddy.R
import com.example.budgetbuddy.TransactionModel

class TrnsctnAdapter(private val trnsctnList: ArrayList<TransactionModel>) :
    RecyclerView.Adapter<TrnsctnAdapter.ViewHolder>() {

    private lateinit var tListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int, isEdit: Boolean)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        tListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.transactions, parent, false)
        val editButtonView = itemView.findViewById<ImageView>(R.id.walletEdit)
        val deleteButtonView = itemView.findViewById<ImageView>(R.id.walletDelete)
        return ViewHolder(itemView, editButtonView, deleteButtonView)
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

    inner class ViewHolder(itemView: View, editButtonView: ImageView, deleteButtonView: ImageView) :
        RecyclerView.ViewHolder(itemView) {
        val tvTrnsctnName: TextView = itemView.findViewById(R.id.mTitle)
        val tvTrnsctnAmount: TextView = itemView.findViewById(R.id.mSubTitleAmount)
        val tvTrnsctnWallet: TextView = itemView.findViewById(R.id.mSubTitleWallet)
        val tvTrnsctnType: TextView = itemView.findViewById(R.id.mSubTitleType)
        val editButton: ImageView = editButtonView
        val deleteButton: ImageView = deleteButtonView

        init {
            editButton.setOnClickListener {
                if (::tListener.isInitialized) {
                    tListener.onItemClick(adapterPosition, true)
                }
            }

            deleteButton.setOnClickListener {
                if (::tListener.isInitialized) {
                    tListener.onItemClick(adapterPosition, false)
                }
            }
        }
    }
}
