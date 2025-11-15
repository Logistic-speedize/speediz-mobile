package com.example.speediz.ui.feature.authorized.vendor.packageTracking

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.speediz.ui.navigation.AuthorizedRoute

fun NavGraphBuilder.screenPackageTracking(
    onNavigateTo : (String) -> Unit
) {
    composable(AuthorizedRoute.VendorRoute.PackageTracking.route) {
        ScreenPackageTracking(
            onNavigateTo = onNavigateTo
        )
    }
}
fun NavController.navigationPackageTracking(){
    this.navigate(AuthorizedRoute.VendorRoute.PackageTracking.route)
}