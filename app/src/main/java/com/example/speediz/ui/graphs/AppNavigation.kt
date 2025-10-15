package com.example.speediz.ui.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.speediz.MainViewModel
import com.example.speediz.ui.navigation.deliveryAuthorizedNavigate
import com.example.speediz.ui.navigation.unauthorizedNavigate
import com.example.speediz.ui.navigation.vendorAuthorizedNavigate

enum class Roles(val value: Int) {
    DELIVERY(1),
    VENDOR(2);
}
@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    isLoggedIn: Boolean,
    onLogin: () -> Unit,
    onLogout: () -> Unit,
    role : Roles = Roles.DELIVERY
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Graph.AUTH_DELIVERY else Graph.UN_AUTH,
        modifier = modifier,
        route = Graph.ROOT,
    ) {
        if (isLoggedIn) {
            if ( role == Roles.VENDOR) {
                 vendorAuthorizedNavigate(
                     navController = navController,
                     mainViewModel = MainViewModel()
                 )
            } else {
                deliveryAuthorizedNavigate(
                    navController = navController,
                    mainViewModel = MainViewModel()
                )
            }
        } else {
            unauthorizedNavigate(
                navController = navController,
                mainViewModel = MainViewModel()
            )
        }
    }
}