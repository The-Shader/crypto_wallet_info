package com.fireblade.cryptowallet.business

sealed class ScreenState {
    object Init: ScreenState()
    object Loading: ScreenState()

    data class Success(
        val walletBalance: Double = 0.0,
        val transactions: List<TransactionItem> = listOf()
    ) : ScreenState()

    data class Error(val error: String) : ScreenState()
}