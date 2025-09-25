package com.example.network.interfaces

import kotlinx.coroutines.flow.StateFlow

interface NetworkAvailabilityRepository {
    val isConnected : StateFlow<Boolean>
}