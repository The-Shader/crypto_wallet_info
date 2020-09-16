package com.fireblade.persistence.repository.utils

import com.fireblade.persistence.data.wallet.DatabaseWallet
import com.fireblade.persistence.model.Wallet

fun DatabaseWallet.toModel() =
    Wallet(
        multiAddress = multiAddress,
        balance = balance
    )