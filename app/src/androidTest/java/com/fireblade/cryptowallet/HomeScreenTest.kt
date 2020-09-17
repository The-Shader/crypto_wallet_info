package com.fireblade.cryptowallet

import android.view.View
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import org.hamcrest.Matcher

class HomeScreen : Screen<HomeScreen>(){
    val walletBalanceText = KTextView { withId(R.id.wallet_balance_text) }
    val walletBalanceValueText = KTextView { withId(R.id.wallet_balance_value) }
    val transactionsList: KRecyclerView = KRecyclerView(
        { withId(R.id.list_of_transactions) },
        itemTypeBuilder = { itemType(::TransactionListItem) }
    )

    class TransactionListItem(parent: Matcher<View>) : KRecyclerItem<TransactionListItem>(parent) {
        val transactionType = KTextView(parent) { withId(R.id.tx_type) }
        val transactonTypeIcon = KImageView(parent) { withId(R.id.tx_type_icon) }
        val transactionValue = KTextView(parent) { withId(R.id.tx_value) }
        val transactionDate = KTextView(parent) { withId(R.id.transaction_date) }
    }
}
