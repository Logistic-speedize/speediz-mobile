package com.example.speediz.ui.utils

import android.content.Context
import android.location.Geocoder
import java.util.Locale

fun geocodeAddress(context: Context, address: String): Pair<Double, Double>? {
    return try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val result = geocoder.getFromLocationName(address, 1)

        if (!result.isNullOrEmpty()) {
            val location = result[0]
            Pair(location.latitude, location.longitude)
        } else null
    } catch (e: Exception) {
        null
    }
}
