package com.fireblade.repository.model

data class Transaction(
    val hash: String,
    val result: Long,
    val time: Long,
    val fee: Long,
)
