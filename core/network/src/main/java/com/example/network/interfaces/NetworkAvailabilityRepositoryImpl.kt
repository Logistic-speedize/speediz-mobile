package com.example.network.interfaces

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NetworkAvailabilityRepositoryImpl(
    val networkConnectivity: NetworkConnectivity,
)
    : NetworkAvailabilityRepository {
        private val _isConnected = MutableStateFlow<Boolean>(true)
    override val isConnected: StateFlow<Boolean> = _isConnected
    init {
        CoroutineScope(Dispatchers.Default).launch {
            networkConnectivity.isOnline.collect {
                _isConnected.value = it
            }
        }
    }
}