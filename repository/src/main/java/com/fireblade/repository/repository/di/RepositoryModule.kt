package com.fireblade.repository.repository.di

import com.fireblade.repository.repository.ChainRepository
import com.fireblade.repository.network.services.IChainApiService
import com.fireblade.repository.repository.IChainRepository
import com.fireblade.repository.data.di.DatabaseModule
import com.fireblade.repository.data.transaction.TransactionDAO
import com.fireblade.repository.data.wallet.WalletDAO
import com.fireblade.repository.network.di.NetworkModule
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class, DatabaseModule::class])
class RepositoryModule {

    @Provides
    fun provideIChainRepository(
        chainApiService: IChainApiService,
        walletDAO: WalletDAO,
        transactionDAO: TransactionDAO
    ): IChainRepository =
        ChainRepository(
            chainApiService,
            walletDAO,
            transactionDAO
        )
}