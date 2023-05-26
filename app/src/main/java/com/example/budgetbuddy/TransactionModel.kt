package com.example.budgetbuddy

data class TransactionModel(
    var trnsctnId: String? = null,
    var addTransactionName: String? = null,
    var addTransactionBalance: String? = null,
    var addTransactionType: String? = null,
    var addTransactionWallet: String? = null
)