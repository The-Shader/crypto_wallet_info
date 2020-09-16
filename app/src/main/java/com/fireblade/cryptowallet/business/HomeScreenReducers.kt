package com.fireblade.cryptowallet.business

import com.fireblade.persistence.repository.utils.Status
import com.fireblade.persistence.model.Transaction
import com.fireblade.persistence.model.Wallet
import javax.inject.Inject

class HomeScreenReducers @Inject constructor() {

    fun reduceWalletBalance(currentState: ScreenState, event: Status<Wallet>) =
        when(event) {
            is Status.Success -> {
                val balanceInBTC = getAbsValueInBTC(event.result.balance)
                when (currentState) {
                    is ScreenState.Success -> currentState.copy(
                        walletBalance = balanceInBTC
                    )
                    else -> ScreenState.Success(
                        walletBalance = balanceInBTC
                    )
                }
            }
            is Status.Failure -> ScreenState.Error(event.error.message ?: "Unknown error occurred")
        }

    fun reduceTransactions(currentState: ScreenState, event: Status<List<Transaction>>) =
        when(event) {
            is Status.Success -> {
                val transactions = event.result.map(Transaction::toItem)
                when (currentState) {
                    is ScreenState.Success -> currentState.copy(
                        transactions = transactions
                    )
                    else -> ScreenState.Success(
                        transactions = transactions
                    )
                }
            }
            is Status.Failure -> ScreenState.Error(event.error.message ?: "Unknown error occurred")
        }
}
