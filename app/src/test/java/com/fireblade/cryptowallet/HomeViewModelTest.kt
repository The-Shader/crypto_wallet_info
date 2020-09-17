package com.fireblade.cryptowallet

import com.appmattus.kotlinfixture.kotlinFixture
import com.babylon.orbit2.assert
import com.babylon.orbit2.test
import com.fireblade.cryptowallet.business.*
import com.fireblade.repository.model.Transaction
import com.fireblade.repository.model.Wallet
import com.fireblade.repository.repository.IChainRepository
import com.fireblade.repository.repository.utils.Status
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.Test

class HomeViewModelTest {

    private val chainRepository = mock<IChainRepository>()
    private val fixture = kotlinFixture()

    // Using the actual state reducer given that it forms a logical unit with the viewmodel
    private val reducers = HomeScreenReducers()

    @Test
    fun `load transactions from repository`() {
        val transactions = fixture<List<Transaction>>()
        val transactionItems = transactions.map { it.toItem() }

        // Mock the repository
        whenever(chainRepository.getTransactions()).thenReturn(Flowable.just(Status.Success(transactions)))

        val homeViewModel = HomeViewModel(reducers, chainRepository).test(
            initialState = ScreenState.Loading,
            runOnCreate = true
        )

        homeViewModel.loadTransactions()


        homeViewModel.assert {
            states(
                { ScreenState.Success(transactions = transactionItems) }
            )
        }
    }

    @Test
    fun `fail to load transactions from repository`() {

        val getTransactionsException = fixture<Exception>()

        // Mock the repository
        whenever(chainRepository.getTransactions()).thenReturn(Flowable.just(Status.Failure(getTransactionsException)))

        val homeViewModel = HomeViewModel(reducers, chainRepository).test(
            initialState = ScreenState.Loading,
            runOnCreate = true
        )
        homeViewModel.loadTransactions()

        homeViewModel.assert {
            states(
                { ScreenState.Error(error = getTransactionsException.message ?: HomeScreenReducers.UNKNOWN_ERROR) }
            )
        }
    }

    @Test
    fun `load wallet balance from repository`() {
        val wallet = fixture<Wallet>()

        // Mock the repository
        whenever(chainRepository.getWallet()).thenReturn(Flowable.just(Status.Success(wallet)))

        val homeViewModel = HomeViewModel(reducers, chainRepository).test(
            initialState = ScreenState.Loading,
            runOnCreate = true
        )

        homeViewModel.loadWallet()

        homeViewModel.assert {
            states(
                { ScreenState.Success(walletBalance = wallet.balance) },
            )
        }
    }

    @Test
    fun `fail to load wallet balance from repository`() {

        val getWalletException = fixture<Exception>()

        // Mock the repository
        whenever(chainRepository.getWallet()).thenReturn(Flowable.just(Status.Failure(getWalletException)))

        val homeViewModel = HomeViewModel(reducers, chainRepository).test(
            initialState = ScreenState.Loading,
            runOnCreate = true
        )

        homeViewModel.loadWallet()

        homeViewModel.assert {
            states(
                { ScreenState.Error(error = getWalletException.message ?: HomeScreenReducers.UNKNOWN_ERROR) },
            )
        }
    }
}