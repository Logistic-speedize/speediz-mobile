package com.example.speediz.ui.feature.authorized.vendor.packageManagement.packageList

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.speediz.ui.navigation.AuthorizedRoute

fun NavGraphBuilder.screenPackage(
    onNavigateTo : (String) -> Unit
) {
    composable(AuthorizedRoute.VendorRoute.Package.route) {
        ScreenPackage(
            onNavigateTo = onNavigateTo
        )
    }
}
fun NavController.navigationPackage(){
    this.navigate(AuthorizedRoute.VendorRoute.Package.route)
}