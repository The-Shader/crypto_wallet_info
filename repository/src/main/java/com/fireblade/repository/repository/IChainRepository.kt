package com.fireblade.repository.repository

import com.fireblade.repository.model.Transaction
import com.fireblade.repository.model.Wallet
import com.fireblade.repository.repository.utils.Status
import io.reactivex.Flowable

interface IChainRepository {

    fun getWallet(): Flowable<Status<Wallet>>

    fun getTransactions(): Flowable<Status<List<Transaction>>>
}