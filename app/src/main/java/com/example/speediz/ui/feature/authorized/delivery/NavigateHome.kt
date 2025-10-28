package com.example.speediz.ui.feature.authorized.delivery

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.example.speediz.ui.navigation.AuthorizedRoute

fun  NavGraphBuilder.screenDeliveryHome(
    onNavigateTo : (String) -> Unit
) {
    composable( route = AuthorizedRoute.DeliveryRoute.Home.route) {
        ScreenHomeDelivery()
    }
}
fun NavController.screenHomeDelivery(
    onNavigateTo : (String) -> Unit
) {
    this.navigate(AuthorizedRoute.DeliveryRoute.Home.route, navOptions
    {
        popUpTo(AuthorizedRoute.DeliveryRoute.Home.route) {
            inclusive = true
        }
    })
}