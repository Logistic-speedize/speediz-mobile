package com.example.speediz.core.network.interceptor

import android.content.Context
import com.example.speediz.ui.utils.NoInternetException
import dagger.hilt.android.internal.Contexts
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor (
    private val context : Context
): Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        if ( !isConnected() ) {
            throw NoInternetException("No Internet Connection")
        } else {
            return chain.proceed(chain.request())
        }
    }
    private fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}