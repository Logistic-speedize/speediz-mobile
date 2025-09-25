package com.example.speediz.ui

import androidx.compose.runtime.Stable
import androidx.navigation.NavController
import com.example.network.interfaces.NetworkConnectivity
import kotlinx.coroutines.CoroutineScope

@Stable
class SPAppState(
    val navController: NavController,
    val hideSplashScreen: Boolean,
    val isAuthenticated: Boolean,
    val coroutineScope: CoroutineScope,
    val networkConnectivity: NetworkConnectivity

) {
}