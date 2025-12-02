package com.example.speediz.ui.feature.authorized.vendor.packageManagement.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.speediz.ui.feature.authorized.vendor.packageTracking.detail.ScreenTrackingDetail
import com.example.speediz.ui.navigation.AuthorizedRoute

fun NavGraphBuilder.screenPackageDetail(
    onBack: () -> Unit
) {
    val route = AuthorizedRoute.VendorRoute.PackageDetail.route
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
        ScreenPackageDetail(
            id = packageId,
            onBack = onBack
        )
    }
}

fun NavController.navigationPackageDetail(
    packageId: String
){
    this.navigate(
        AuthorizedRoute.VendorRoute.PackageDetail.route.replace("{id}", packageId)
    )
}