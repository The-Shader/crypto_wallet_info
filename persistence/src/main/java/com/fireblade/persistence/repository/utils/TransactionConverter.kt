package com.fireblade.persistence.repository.utils

import com.fireblade.persistence.data.transaction.DatabaseTransaction
import com.fireblade.persistence.model.Transaction
import com.fireblade.persistence.network.domain.NetworkTransaction

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