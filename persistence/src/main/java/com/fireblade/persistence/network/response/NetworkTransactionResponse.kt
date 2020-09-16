package com.fireblade.persistence.network.response

import com.fireblade.persistence.network.domain.NetworkTransaction

data class NetworkTransactionResponse(
    val txs: List<NetworkTransaction>
)