package com.example.moviediscovery.util

import java.util.Locale
import kotlin.math.ceil

fun Float.formatRatingToOneDecimalUp(): String {
    val rounded = ceil(this * 10) / 10f
    return String.format(Locale.US, "%.1f", rounded)
}