package com.fireblade.repository.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fireblade.repository.data.transaction.DatabaseTransaction
import com.fireblade.repository.data.transaction.TransactionDAO
import com.fireblade.repository.data.wallet.DatabaseWallet
import com.fireblade.repository.data.wallet.WalletDAO

@Database(entities = [DatabaseWallet::class, DatabaseTransaction::class], version = 1, exportSchema = false)
abstract class WalletDatabase : RoomDatabase() {

    abstract fun walletDao(): WalletDAO

    abstract fun transactionDao(): TransactionDAO

    companion object {
        @Volatile
        private var INSTANCE: WalletDatabase? = null

        fun getInstance(context: Context): WalletDatabase {

            return INSTANCE ?: synchronized(this) {

                INSTANCE
                    ?: createDatabase(context).also { instance ->
                        INSTANCE = instance
                    }
            }
        }

        private fun createDatabase(context: Context): WalletDatabase {

            return Room.databaseBuilder(
                context.applicationContext,
                WalletDatabase::class.java,
                "Wallet_Database"
            )
                .build()
        }
    }
}