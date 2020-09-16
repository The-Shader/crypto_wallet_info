package com.fireblade.cryptowallet.business

import kotlin.math.abs

fun getAbsValueInBTC(value: Long) = abs(value.toDouble() * 1.0 / 1e8)