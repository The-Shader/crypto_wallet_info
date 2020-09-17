package com.fireblade.cryptowallet.business

import com.fireblade.repository.repository.utils.Status
import com.fireblade.repository.model.Transaction
import com.fireblade.repository.model.Wallet
import javax.inject.Inject

class HomeScreenReducers @Inject constructor() {

    fun reduceWalletBalance(currentState: ScreenState, event: Status<Wallet>) =
        when(event) {
            is Status.Success -> {
                when (currentState) {
                    is ScreenState.Success -> currentState.copy(
                        walletBalance = event.result.balance
                    )
                    else -> ScreenState.Success(
                        walletBalance = event.result.balance
                    )
                }
            }
            is Status.Failure -> ScreenState.Error(event.error.message ?: UNKNOWN_ERROR)
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
            is Status.Failure -> ScreenState.Error(event.error.message ?: UNKNOWN_ERROR)
        }

    companion object {
        const val UNKNOWN_ERROR = "Unknown error occurred"
    }
}

fun Transaction.toItem() =
    TransactionItem(
        valueInBTC = getAbsValueInBTC(result),
        time = time,
        hash = hash,
        feeInBtc = getAbsValueInBTC(fee),
        transactionType = if (result < 0) TransactionType.SENT else TransactionType.RECEIVED
    )
