package com.example.speediz.ui.feature.authorized.delivery.express.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.speediz.ui.navigation.AuthorizedRoute


fun NavGraphBuilder.screenExpressDetail(
    navigateTo: (String) -> Unit,
    onBack: () -> Unit
) {
    composable(
        route = AuthorizedRoute.DeliveryRoute.ExpressDetail.route,
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
        val expressId = backStackEntry.arguments?.getString("id") ?: ""
        ScreenExpressDetail(
            onBack = onBack,
            navigateTo = navigateTo
        )
    }
}
fun NavController.navigationExpressDetail(
    expressId: String
) {
    this.navigate(
        AuthorizedRoute.DeliveryRoute.ExpressDetail.route.replace("{id}", expressId)
    )

}