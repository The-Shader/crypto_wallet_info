package com.fireblade.repository.repository

import com.fireblade.repository.data.transaction.DatabaseTransaction
import com.fireblade.repository.data.transaction.TransactionDAO
import com.fireblade.repository.data.wallet.DatabaseWallet
import com.fireblade.repository.data.wallet.WalletDAO
import com.fireblade.repository.model.Transaction
import com.fireblade.repository.model.Wallet
import com.fireblade.repository.network.domain.NetworkTransaction
import com.fireblade.repository.network.services.IChainApiService
import com.fireblade.repository.repository.utils.Status
import com.fireblade.repository.repository.utils.toEntity
import com.fireblade.repository.repository.utils.toModel
import io.reactivex.Flowable
import java.lang.Exception
import javax.inject.Inject

class ChainRepository @Inject constructor(
    private val chainApiService: IChainApiService,
    private val walletDAO: WalletDAO,
    private val transactionDAO: TransactionDAO,
) : IChainRepository {

    // This is here for simplicity, should store it in a secured way
    private val ownXPubAddress =
        "xpub6CfLQa8fLgtouvLxrb8EtvjbXfoC1yqzH6YbTJw4dP7srt523AhcMV8Uh4K3TWSHz9oDWmn9MuJogzdGU3ncxkBsAC9wFBLmFrWT9Ek81kQ"

    // Both get functions are attempting to sync the local data with the network by obtaining the
    // data from the database if there's any, then trying to get the latest from the network. On success
    // save it to the database and return it here.
    override fun getWallet(): Flowable<Status<Wallet>> {
        return Flowable.concat(
            syncWallet()
                .map<Status<Wallet>> {
                    Status.Success(it)
                }
                .onErrorReturn {
                    Status.Failure(Exception("Network sync error"))
                },
            walletDAO.getWallet(ownXPubAddress)
                .map<Status<Wallet>> {
                    Status.Success(it.toModel())
                }
                .onErrorReturn {
                    Status.Failure(Exception("Local cache error"))
                }
        )
    }

    override fun getTransactions(): Flowable<Status<List<Transaction>>> {
        return Flowable.concat(
            syncTransactions()
                .map<Status<List<Transaction>>> {
                Status.Success(it)
                }
                .onErrorReturn {
                    Status.Failure(Exception("Network sync error"))
                },
            transactionDAO.getAllTransactions()
                .map<Status<List<Transaction>>> {
                    Status.Success(it.map(DatabaseTransaction::toModel))
                }
                .onErrorReturn {
                    Status.Failure(Exception("Local cache error"))
                }
        )
    }

    private fun syncWallet(): Flowable<Wallet> {
        return Flowable.concat(
            walletDAO.getWalletMaybe(ownXPubAddress).toFlowable()
                .map {
                    it.toModel()
                },
            chainApiService.getWallet(ownXPubAddress)
                .map {
                    DatabaseWallet(
                        ownXPubAddress,
                        it.wallet.finalBalance
                    )
                }.flatMapCompletable(walletDAO::insertWallet)
                .onErrorComplete()
                .toFlowable()
        ).distinctUntilChanged()
    }

    private fun syncTransactions(): Flowable<List<Transaction>> {
        return Flowable.concat(
            transactionDAO.getAllTransactionsMaybe()
                .map {
                    it.map(DatabaseTransaction::toModel)
                }.toFlowable(),
            chainApiService.getTransactions(ownXPubAddress)
                .map {
                    it.txs.map(NetworkTransaction::toEntity)
                }
                .flatMapCompletable(transactionDAO::insertTransactions)
                .onErrorComplete()
                .toFlowable()
        ).distinctUntilChanged()
    }
}