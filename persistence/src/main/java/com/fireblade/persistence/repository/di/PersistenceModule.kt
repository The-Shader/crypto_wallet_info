package com.fireblade.persistence.repository.di

import com.fireblade.persistence.repository.ChainRepository
import com.fireblade.persistence.network.services.IChainApiService
import com.fireblade.persistence.repository.IChainRepository
import com.fireblade.persistence.data.di.DatabaseModule
import com.fireblade.persistence.data.transaction.TransactionDAO
import com.fireblade.persistence.data.wallet.WalletDAO
import com.fireblade.persistence.network.di.NetworkModule
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class, DatabaseModule::class])
class PersistenceModule {

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