package com.fireblade.repository.data.transaction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface TransactionDAO {

    @Query("select * from Transactions")
    fun getAllTransactions(): Flowable<List<DatabaseTransaction>>

    @Query("select * from Transactions")
    fun getAllTransactionsMaybe(): Maybe<List<DatabaseTransaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransactions(transactions: List<DatabaseTransaction>): Completable

    @Query("delete from Transactions")
    fun deleteTransactions()
}