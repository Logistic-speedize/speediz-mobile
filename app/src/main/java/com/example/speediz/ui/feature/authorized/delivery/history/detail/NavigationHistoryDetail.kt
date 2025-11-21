package com.example.speediz.ui.feature.authorized.delivery.history.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.speediz.ui.navigation.AuthorizedRoute

fun NavGraphBuilder.screenHistoryDetail(
    onBack: () -> Unit
) {
    val route = AuthorizedRoute.DeliveryRoute.HistoryDetail.route
    val argumentName = listOf(
        navArgument("id") {
            type = NavType.StringType
        }
    )
    composable(
        route = route,
        arguments = argumentName
    ) { backStackEntry ->
        val packageId = backStackEntry.arguments?.getString("id") ?: ""
        ScreenHistoryDetail(
            id = packageId,
            onBack = onBack
        )
    }
}


fun NavController.navigateToHistoryDetail(packageId: String) {
    this.navigate(
        AuthorizedRoute.DeliveryRoute.HistoryDetail.route.replace("{id}", packageId)
    )
}