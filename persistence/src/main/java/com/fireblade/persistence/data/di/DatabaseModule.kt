package com.fireblade.persistence.data.di

import android.content.Context
import com.fireblade.persistence.data.WalletDatabase
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