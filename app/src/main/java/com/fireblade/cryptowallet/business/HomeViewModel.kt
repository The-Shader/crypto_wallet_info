package com.fireblade.cryptowallet.business

import androidx.lifecycle.ViewModel
import com.babylon.orbit2.ContainerHost
import com.babylon.orbit2.reduce
import com.babylon.orbit2.rxjava2.transformRx2Observable
import com.babylon.orbit2.viewmodel.container
import com.fireblade.repository.repository.IChainRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val reducers: HomeScreenReducers,
    private val chainRepository: IChainRepository
) : ContainerHost<ScreenState, Nothing>, ViewModel() {

    override val container = container<ScreenState, Nothing>(ScreenState.Loading)

    fun loadWallet() = orbit {
        transformRx2Observable {
            chainRepository.getWallet().toObservable()
        }
            .reduce {
                reducers.reduceWalletBalance(state, event)
            }
    }

    fun loadTransactions() = orbit {
        transformRx2Observable {
            chainRepository.getTransactions().toObservable()
        }
            .reduce {
                reducers.reduceTransactions(state, event)
            }
    }
}