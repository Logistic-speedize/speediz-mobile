package com.example.speediz.ui.feature.authorized.delivery.map

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.speediz.ui.navigation.AuthorizedRoute

fun NavGraphBuilder.screenMap(
    navigateTo: (String) -> Unit
) {
    composable(AuthorizedRoute.DeliveryRoute.Map.route){
        ScreenMap()
    }
}
fun NavController.navigationMap() {
}