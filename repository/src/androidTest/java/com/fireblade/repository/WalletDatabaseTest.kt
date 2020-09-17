package com.fireblade.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fireblade.repository.data.WalletDatabase
import com.fireblade.repository.data.transaction.DatabaseTransaction
import com.fireblade.repository.data.transaction.TransactionDAO
import com.fireblade.repository.data.wallet.DatabaseWallet
import com.fireblade.repository.data.wallet.WalletDAO
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.hamcrest.core.IsEqual.equalTo
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class WalletDatabaseTest {

    private lateinit var walletDAO: WalletDAO
    private lateinit var transactionDAO: TransactionDAO
    private lateinit var walletDatabase: WalletDatabase
    private val schedulers = Schedulers.trampoline()
    private lateinit var compositeDisposable: CompositeDisposable
    @JvmField @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        compositeDisposable = CompositeDisposable()
        walletDatabase = Room.inMemoryDatabaseBuilder(context, WalletDatabase::class.java).allowMainThreadQueries().build()
        walletDAO = walletDatabase.walletDao()
        transactionDAO = walletDatabase.transactionDao()
    }

    @After
    @Throws(IOException::class)
    fun shutdown() {
        walletDatabase.close()
        compositeDisposable.clear()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndQueryWalletInfo() {
        val wallet = createWalletTestData()
        walletDAO.insertWallet(wallet).observeOn(schedulers).subscribe()
        var walletFromDB: DatabaseWallet? = null
        walletDAO.getWallet(wallet.multiAddress).observeOn(schedulers).subscribe {
            walletFromDB = it
        }.apply {
            compositeDisposable.add(this)
        }
        assertThat(walletFromDB, equalTo(wallet))
    }

    @Test
    @Throws(Exception::class)
    fun insertAndQueryTransactions() {
        val transactions = listOf(createTransactionTestData("hash1"), createTransactionTestData("hash2"))
        transactionDAO.insertTransactions(transactions).observeOn(schedulers).subscribe()
        var allTransactions: List<DatabaseTransaction> = listOf()
        transactionDAO.getAllTransactions().observeOn(schedulers).subscribe {
            allTransactions = it
        }.apply {
            compositeDisposable.add(this)
        }
        assertThat(allTransactions.size, equalTo(transactions.size))
        assertThat(allTransactions, equalTo(transactions))
    }

    @Test
    @Throws(Exception::class)
    fun insertAndDeleteTransactions() {
        val transactions = listOf(createTransactionTestData("hash1"), createTransactionTestData("hash2"))
        transactionDAO.insertTransactions(transactions).observeOn(schedulers).subscribe()
        transactionDAO.deleteTransactions().observeOn(schedulers).subscribe()
        var allTransactions: List<DatabaseTransaction> = listOf()
        transactionDAO.getAllTransactions().observeOn(schedulers).subscribe {
            allTransactions = it
        }.apply {
            compositeDisposable.add(this)
        }
        assertEquals(0, allTransactions.size)
        assertNotEquals(transactions, allTransactions)
    }

    private fun createWalletTestData(): DatabaseWallet {
        return DatabaseWallet(
            multiAddress = "xpub6CfLQa8fLgtouvLxrb8EtvjbXfoC1yqzH6YbTJw4dP7srt523AhcMV8Uh4K3TWSHz9oDWmn9MuJogzdGU3ncxkBsAC9wFBLmFrWT9Ek81kQ",
            balance = 10
        )
    }

    private fun createTransactionTestData(hash: String): DatabaseTransaction {
        return DatabaseTransaction(
            hash = hash,
            result = -2,
            time = 5,
            fee = 3
        )
    }
}
