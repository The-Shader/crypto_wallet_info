package com.fireblade.cryptowallet.ui

import com.fireblade.cryptowallet.R
import com.fireblade.cryptowallet.business.TransactionItem
import com.fireblade.cryptowallet.business.TransactionType
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.transaction_list_item.view.*
import java.time.format.DateTimeFormatter

class TransactionViewItem(private val transactionItem: TransactionItem, private val dateTimeFormatter: DateTimeFormatter): Item() {

    override fun getLayout(): Int = R.layout.transaction_list_item

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.apply {
            tx_value.text = resources.getString(R.string.btc_value, transactionItem.valueInBTC.toBigDecimal().setScale(8).toString())
            transaction_date.text = transactionItem.time.format(dateTimeFormatter)

            when(transactionItem.transactionType) {
                TransactionType.SENT -> {
                    tx_type.text = resources.getString(R.string.sent_btc_label)
                    tx_type_icon.setImageResource(R.drawable.ic_circular_arrow_sent)
                }
                TransactionType.RECEIVED -> {
                    tx_type.text = resources.getString(R.string.sent_btc_label)
                    tx_type_icon.setImageResource(R.drawable.ic_circular_arrow_received)
                }
            }
        }

    }
}