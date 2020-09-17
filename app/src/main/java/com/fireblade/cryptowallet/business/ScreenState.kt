package com.fireblade.cryptowallet.business

// In the future it's probably better to split the result state for the wallet and the transactions
// into separate sub-classes
sealed class ScreenState {
    object Loading: ScreenState()

    data class Success(
        val walletBalance: Long = 0,
        val transactions: List<TransactionItem> = listOf()
    ) : ScreenState()

    data class Error(val error: String) : ScreenState()
}