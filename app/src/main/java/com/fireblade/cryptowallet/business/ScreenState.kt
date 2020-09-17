package com.fireblade.cryptowallet.business

sealed class ScreenState {
    object Loading: ScreenState()

    data class Success(
        val walletBalance: Long = 0,
        val transactions: List<TransactionItem> = listOf()
    ) : ScreenState()

    data class Error(val error: String) : ScreenState()
}