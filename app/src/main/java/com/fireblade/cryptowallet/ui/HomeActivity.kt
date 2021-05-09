package com.fireblade.cryptowallet.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.fireblade.cryptowallet.R
import com.fireblade.cryptowallet.business.HomeViewModel
import com.fireblade.cryptowallet.business.ScreenState
import com.fireblade.cryptowallet.business.getAbsValueInBTC
import com.fireblade.repository.network.services.IChainApiService
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.flow.collect
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var chainService: IChainApiService

    @Inject
    lateinit var viewModel: HomeViewModel

    private val transactionsAdapter: GroupAdapter<GroupieViewHolder> by lazy { GroupAdapter<GroupieViewHolder>() }

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

    override fun androidInjector(): DispatchingAndroidInjector<Any> = androidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        list_of_transactions.adapter = transactionsAdapter

        list_of_transactions.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
                .apply {
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.divider_item_decoration,
                        null
                    )?.let { decoration ->
                        setDrawable(decoration)
                    }
                }
        )

        lifecycleScope.launchWhenCreated {
            viewModel.container.stateFlow.collect { state ->
                render(state)
            }
        }
        viewModel.loadWallet()
        viewModel.loadTransactions()
    }

    private fun render(screenState: ScreenState) {
        when(screenState) {
            ScreenState.Loading -> progress_bar.visibility = View.VISIBLE
            is ScreenState.Success -> {
                hideProgressBar()
                setWalletInfo(screenState)
                setTransactions(screenState)
            }
            is ScreenState.Error -> showErrorState(screenState)
        }
    }

    private fun setWalletInfo(screenState: ScreenState.Success) {
        // Prevent displaying a lot of zeros when the balance is 0
        val numOfDecimals = if (screenState.walletBalance == 0.toLong()) 1 else 8
        wallet_balance_value.text = getString(
            R.string.btc_value,
            getAbsValueInBTC(screenState.walletBalance).toBigDecimal()
                .setScale(numOfDecimals).toString()
        )
    }

    private fun setTransactions(screenState: ScreenState.Success) {
        transactionsAdapter.clear()
        transactionsAdapter.addAll(
            screenState.transactions.map {
                TransactionViewItem(it, dateFormatter)
            }
        )
    }

    private fun showErrorState(screenState: ScreenState.Error) {
        hideProgressBar()
        Toast.makeText(this, screenState.error, Toast.LENGTH_SHORT).show()
    }

    private fun hideProgressBar() {
        progress_bar.visibility = View.GONE
    }
}