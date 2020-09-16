package com.fireblade.repository.network.domain

data class NetworkTransaction(
    val hash: String,
    val result: Long,
    val time: Long,
    val fee: Long
)
