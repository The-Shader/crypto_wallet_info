package com.fireblade.repository.model

// This is currently not used, I added it for model completeness and for easier future improvements
data class Wallet(
    val multiAddress: String,
    val balance: Long
)