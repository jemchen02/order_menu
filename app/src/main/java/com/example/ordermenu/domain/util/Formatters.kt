package com.example.ordermenu.domain.util

import com.google.firebase.Timestamp
import java.text.NumberFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

fun getMinutesAgo(timeStamp: Timestamp): String {
    val now = System.currentTimeMillis()
    val diff = now - timeStamp.toDate().time

    val minutesAgo = TimeUnit.MILLISECONDS.toMinutes(diff) % 60
    val hoursAgo = TimeUnit.MILLISECONDS.toHours(diff)
    if(hoursAgo > 0) {
        return "$hoursAgo hr, $minutesAgo min ago"
    }
    return "$minutesAgo min ago"
}

fun getPriceString(price: Double): String {
    return NumberFormat.getCurrencyInstance(Locale.US).format(price)
}