package com.example.network.interfaces

import kotlinx.coroutines.flow.Flow

interface NetworkConnectivity {
    val isOnline : Flow<Boolean>
    fun isNetworkConnected(): Boolean
    fun getIpAddress(): String
    fun connectivityType(): String
}