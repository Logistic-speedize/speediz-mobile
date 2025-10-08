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

    fun navigateTo(route: String) {
        if (navController.currentDestination?.route != route) {
            navController.navigate(route)
        }
    }
    fun navigateUp() {
        navController.navigateUp()
    }
    fun clearBackStack(route: String) {
        navController.popBackStack(route, inclusive = false)
    }
    fun clearAllBackStack() {
        navController.popBackStack(navController.graph.startDestinationId, false)
    }

}
fun navController(
    navController: NavController,
    hideSplashScreen: Boolean,
    isAuthenticated: Boolean,
    coroutineScope: CoroutineScope,
    networkConnectivity: NetworkConnectivity
) = SPAppState(
    navController = navController,
    hideSplashScreen = hideSplashScreen,
    isAuthenticated = isAuthenticated,
    coroutineScope = coroutineScope,
    networkConnectivity = networkConnectivity
)
