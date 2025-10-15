package com.example.speediz.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.speediz.MainViewModel
import com.example.speediz.ui.feature.authorized.delivery.Package.screenPackage
import com.example.speediz.ui.feature.authorized.delivery.account.screenAccount
import com.example.speediz.ui.feature.authorized.delivery.history.screenHistory
import com.example.speediz.ui.feature.authorized.delivery.map.screenMap
import com.example.speediz.ui.graphs.Graph

fun NavGraphBuilder.deliveryAuthorizedNavigate(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    navigation(
        startDestination = AuthorizedRoute.DeliveryRoute.Home.route ,
        route = Graph.AUTH_DELIVERY
    ) {
       screenMap()
        screenPackage()
        screenAccount()
        screenHistory()
    }
}
fun NavGraphBuilder.vendorAuthorizedNavigate(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    navigation(
        startDestination = AuthorizedRoute.VendorRoute.Home.route ,
        route = Graph.AUTH_VENDOR
    ) {
    }
}