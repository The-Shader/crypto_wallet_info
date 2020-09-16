package com.fireblade.persistence.repository

import com.fireblade.persistence.model.Transaction
import com.fireblade.persistence.model.Wallet
import com.fireblade.persistence.repository.utils.Status
import io.reactivex.Flowable

interface IChainRepository {

    fun getWallet(): Flowable<Status<Wallet>>

    fun getTransactions(): Flowable<Status<List<Transaction>>>
}