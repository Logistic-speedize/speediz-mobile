package com.example.speediz.ui.feature.authorized.vendor.packageTracking.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.speediz.ui.navigation.AuthorizedRoute

fun NavGraphBuilder.screenTrackingDetail(
    onNavigateTo : (String) -> Unit,
    onBack: () -> Unit
) {
    val route = AuthorizedRoute.VendorRoute.PackageTrackingDetail.route
    val argumentName = listOf(navArgument("id") { type = NavType.StringType })
    composable(
        route = route,
        arguments = argumentName
    ) { backStackEntry ->
        val packageId = backStackEntry.arguments?.getString("id") ?: ""
        ScreenTrackingDetail(
            id = packageId,
            onNavigateTo = onNavigateTo,
            onBack = onBack
        )
    }
}

fun NavController.navigationTrackingDetail(
    packageId: String
){
    this.navigate(
        AuthorizedRoute.VendorRoute.PackageTrackingDetail.route.replace("{id}", packageId)
    )
}