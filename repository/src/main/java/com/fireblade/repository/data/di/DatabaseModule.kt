package com.fireblade.repository.data.di

import android.content.Context
import com.fireblade.repository.data.WalletDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(context: Context): WalletDatabase = WalletDatabase.getInstance(context)

    @Provides
    fun provideWalletDAO(walletDatabase: WalletDatabase) = walletDatabase.walletDao()

    @Provides
    fun provideTransactionDAO(walletDatabase: WalletDatabase) = walletDatabase.transactionDao()
}