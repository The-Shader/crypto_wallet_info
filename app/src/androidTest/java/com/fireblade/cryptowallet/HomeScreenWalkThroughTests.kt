package com.fireblade.cryptowallet

import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.common.utilities.getResourceString
import com.fireblade.cryptowallet.ui.HomeActivity
import org.junit.Rule
import org.junit.Test

class HomeScreenWalkThroughTests {

    @Rule
    @JvmField
    var rule = ActivityTestRule(HomeActivity::class.java)

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