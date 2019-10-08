package com.exchangeRates.core.ext

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun Long.Companion.toFormattedDate(value: Long) =
    SimpleDateFormat("EEE, d MMM HH:mm", Locale.US).format(Date(TimeUnit.SECONDS.toMillis(value)))!!