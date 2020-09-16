package com.fireblade.repository.repository.utils

import com.fireblade.repository.data.wallet.DatabaseWallet
import com.fireblade.repository.model.Wallet

fun DatabaseWallet.toModel() =
    Wallet(
        multiAddress = multiAddress,
        balance = balance
    )