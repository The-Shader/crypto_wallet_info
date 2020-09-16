package com.fireblade.repository.data.wallet

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface WalletDAO {
    @Query("select * from Wallet where multiAddress = :multiAddress")
    fun getWallet(multiAddress: String): Flowable<DatabaseWallet>

    @Query("select * from Wallet where multiAddress = :multiAddress")
    fun getWalletMaybe(multiAddress: String): Maybe<DatabaseWallet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWallet(wallet: DatabaseWallet): Completable

    @Query("delete from Wallet")
    fun deleteWallet()
}