package com.fireblade.cryptowallet.business

data class TransactionItem(
    val valueInBTC: Double,
    val time: Long,
    val hash: String,
    val feeInBtc: Double,
    val transactionType: TransactionType
)

enum class TransactionType {
    RECEIVED,
    SENT
}