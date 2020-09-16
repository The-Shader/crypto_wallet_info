package com.fireblade.repository.data.wallet

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Wallet")
data class DatabaseWallet(
    @PrimaryKey
    val multiAddress: String,
    val balance: Long
)