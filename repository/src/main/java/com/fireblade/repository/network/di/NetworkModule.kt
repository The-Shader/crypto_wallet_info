package com.fireblade.repository.network.di

import android.content.Context
import com.fireblade.repository.network.services.ChainApiSdk
import com.fireblade.repository.network.services.HttpClientBuilder
import com.fireblade.repository.network.services.IChainApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideChainApiService(chainApiSdk: ChainApiSdk) : IChainApiService =
        IChainApiService.create(chainApiSdk)

    @Provides
    fun provideChainApiSdk(httpClient: OkHttpClient, rxJava2CallAdapterFactory: RxJava2CallAdapterFactory, gsonConverterFactory: GsonConverterFactory) =
        ChainApiSdk(
            httpClient,
            rxJava2CallAdapterFactory,
            gsonConverterFactory
        )

    @Provides
    fun provideOkHttpClient(context: Context) =
        HttpClientBuilder(
            context
        ).build()

    @Provides
    fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()
}