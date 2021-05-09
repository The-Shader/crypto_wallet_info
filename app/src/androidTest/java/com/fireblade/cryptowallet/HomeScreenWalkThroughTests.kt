package com.fireblade.cryptowallet

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.agoda.kakao.common.utilities.getResourceString
import com.fireblade.cryptowallet.ui.HomeActivity
import org.junit.Rule
import org.junit.Test

class HomeScreenWalkThroughTests {

    @Rule
    @JvmField
    var rule = ActivityScenarioRule(HomeActivity::class.java)

    private val homeScreen = HomeScreen()

    @Test
    fun load_home_screen() {
        homeScreen {
            walletBalanceText.isDisplayed()
            walletBalanceText.hasText(getResourceString(R.string.balance_label))
            walletBalanceValueText.isDisplayed()
            transactionsList.isDisplayed()

            transactionsList {
                firstChild<HomeScreen.TransactionListItem> {
                    isVisible()
                    transactionType.hasAnyText()
                    transactonTypeIcon.isVisible()
                    transactionValue.hasAnyText()
                    transactionDate.hasAnyText()
                }
            }
        }
    }
}