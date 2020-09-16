package com.fireblade.cryptowallet.business

import java.time.LocalDateTime

data class TransactionItem(
    val valueInBTC: Double,
    val time: LocalDateTime,
    val hash: String,
    val feeInBtc: Double,
    val transactionType: TransactionType
)

enum class TransactionType {
    RECEIVED,
    SENT
}