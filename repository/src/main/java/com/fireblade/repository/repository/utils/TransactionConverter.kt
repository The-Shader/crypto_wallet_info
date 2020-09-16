package com.fireblade.repository.repository.utils

import com.fireblade.repository.data.transaction.DatabaseTransaction
import com.fireblade.repository.model.Transaction
import com.fireblade.repository.network.domain.NetworkTransaction

fun NetworkTransaction.toEntity(): DatabaseTransaction {
    return DatabaseTransaction(
        hash = hash,
        result = result,
        time = time,
        fee = fee
    )
}

fun DatabaseTransaction.toModel(): Transaction {
    return Transaction(
        hash = hash,
        result = result,
        time = time,
        fee = fee
    )
}