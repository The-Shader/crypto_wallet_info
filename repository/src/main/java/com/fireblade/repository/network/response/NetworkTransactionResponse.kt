package com.fireblade.repository.network.response

import com.fireblade.repository.network.domain.NetworkTransaction

data class NetworkTransactionResponse(
    val txs: List<NetworkTransaction>
)