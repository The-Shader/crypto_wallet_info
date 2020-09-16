package com.fireblade.repository.network.services

import com.fireblade.repository.network.response.NetworkTransactionResponse
import com.fireblade.repository.network.response.NetworkWalletResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IChainApiService {
    @GET("/multiaddr")
    fun getWallet(@Query("active") xPubAddress: String) : Single<NetworkWalletResponse>

    @GET("/multiaddr")
    fun getTransactions(@Query("active") xPubAddress: String) : Single<NetworkTransactionResponse>

    companion object Factory {

        fun create(chainApiSdk: ChainApiSdk): IChainApiService = chainApiSdk.getClient().create(
            IChainApiService::class.java)
    }
}