package com.example.speediz.ui.feature.authorized.delivery.history

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import com.example.speediz.ui.navigation.AuthorizedRoute

fun NavGraphBuilder.screenHistory(
    onNavigateTo : ( String) -> Unit
) {
    composable(AuthorizedRoute.DeliveryRoute.History.route){
        ScreenHistory()
    }
}
fun NavController.navigationHistory() {
}