package com.fireblade.cryptowallet.business

import androidx.lifecycle.ViewModel
import com.fireblade.repository.repository.IChainRepository
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.reactive.asFlow
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

// The core of the MVI Orbit architecture is the Orbit-Android ViewModel, with a Container which
// takes care of operating the reactive streams on background (computation and IO) threads. Important
// note, that while it has an RX plugin to ease working Rx-style, it uses coroutines internally.
class HomeViewModel @Inject constructor(
    private val reducers: HomeScreenReducers,
    private val chainRepository: IChainRepository
) : ContainerHost<ScreenState, Nothing>, ViewModel() {

    override val container = container<ScreenState, Nothing>(ScreenState.Loading)

    fun loadWallet() = intent {
        chainRepository.getWallet().subscribeOn(Schedulers.io()).asFlow().collect { result ->
            reduce {
                reducers.reduceWalletBalance(state, result)
            }
        }
    }

    fun loadTransactions() = intent {

        chainRepository.getTransactions().subscribeOn(Schedulers.io()).asFlow().collect { result ->
            reduce {
                reducers.reduceTransactions(state, result)
            }
        }
    }
}