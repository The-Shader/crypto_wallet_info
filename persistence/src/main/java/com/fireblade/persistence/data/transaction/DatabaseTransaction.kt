package com.fireblade.persistence.data.transaction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Transactions")
data class DatabaseTransaction(
    @PrimaryKey
    val hash: String,
    val result: Long,
    val time: Long,
    val fee: Long
)