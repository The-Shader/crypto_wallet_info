package com.fireblade.repository.network.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ChainApiSdk @Inject constructor(
    private val httpClient: OkHttpClient,
    private val rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
    private val gsonConverterFactory: GsonConverterFactory
) {

    private val retrofit by lazy {

        Retrofit.Builder()
            .baseUrl("https://blockchain.info")
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .addConverterFactory(gsonConverterFactory)
            .client(httpClient)
            .build()
    }

    fun getClient() : Retrofit {
        return retrofit
    }
}