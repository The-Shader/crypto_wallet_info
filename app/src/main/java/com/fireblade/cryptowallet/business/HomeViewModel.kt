package com.fireblade.cryptowallet.business

import androidx.lifecycle.ViewModel
import com.babylon.orbit2.ContainerHost
import com.babylon.orbit2.reduce
import com.babylon.orbit2.rxjava2.transformRx2Observable
import com.babylon.orbit2.viewmodel.container
import com.fireblade.repository.repository.IChainRepository
import javax.inject.Inject

// The core of the MVI Orbit architecture is the Orbit-Android ViewModel, with a Container which
// takes care of operating the reactive streams on background (computation and IO) threads. Important
// note, that while it has an RX plugin to ease working Rx-style, it uses coroutines internally.
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