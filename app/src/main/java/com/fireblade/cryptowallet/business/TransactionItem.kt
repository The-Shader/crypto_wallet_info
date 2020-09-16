package com.fireblade.cryptowallet.business

import com.fireblade.persistence.model.Transaction
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

data class TransactionItem(
    val valueInBTC: Double,
    val time: LocalDateTime,
    val hash: String,
    val feeInBtc: Double,
    val transactionType: TransactionType
)

fun Transaction.toItem() =
    TransactionItem(
        valueInBTC = getAbsValueInBTC(result),
        time = LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault()),
        hash = hash,
        feeInBtc = getAbsValueInBTC(fee),
        transactionType = if (result < 0) TransactionType.SENT else TransactionType.RECEIVED
    )

enum class TransactionType {
    RECEIVED,
    SENT
}