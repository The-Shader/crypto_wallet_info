package com.fireblade.repository.network.domain

import com.google.gson.annotations.SerializedName

data class NetworkWallet(
    @SerializedName("final_balance")
    val finalBalance: Long
)