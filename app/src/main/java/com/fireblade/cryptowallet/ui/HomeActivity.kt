package com.fireblade.cryptowallet.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.babylon.orbit2.livedata.state
import com.fireblade.cryptowallet.R
import com.fireblade.cryptowallet.business.HomeViewModel
import com.fireblade.cryptowallet.business.ScreenState
import com.fireblade.persistence.network.services.IChainApiService
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var chainService: IChainApiService

    @Inject
    lateinit var viewModel: HomeViewModel

    private val transactionsAdapter: GroupAdapter<GroupieViewHolder> by lazy { GroupAdapter<GroupieViewHolder>() }

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): DispatchingAndroidInjector<Any> = androidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        list_of_transactions.adapter = transactionsAdapter

        list_of_transactions.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL).apply {
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.divider_item_decoration,
                null
            )?.let { decoration ->
                setDrawable(decoration)
            }
        })

        viewModel.container.state.observe(this, {
            render(it)
        })
    }

    private fun render(screenState: ScreenState) {
        when(screenState) {
            ScreenState.Loading -> progress_bar.visibility = View.VISIBLE
            is ScreenState.Success -> {
                progress_bar.visibility = View.GONE
                setTransactions(screenState)
            }
            is ScreenState.Error -> showErrorState(screenState)
        }
    }

    private fun setTransactions(screenState: ScreenState.Success) {
        val numOfDecimals = if (screenState.walletBalance == 0.0) 1 else 8
        wallet_balance_value.text = getString(R.string.btc_value, screenState.walletBalance.toBigDecimal().setScale(numOfDecimals).toString())
        transactionsAdapter.clear()
        transactionsAdapter.addAll(
            screenState.transactions.map {
                TransactionViewItem(it)
            }
        )
    }

    private fun showErrorState(screenState: ScreenState.Error) {
        progress_bar.visibility = View.GONE
        Toast.makeText(this, screenState.error, Toast.LENGTH_SHORT).show()
    }
}