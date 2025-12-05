package com.example.speediz.ui.feature.authorized.vendor.packageManagement.create

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.speediz.ui.navigation.AuthorizedRoute

fun NavGraphBuilder.screenCreatePackage(
    onBack: () -> Unit,
    onCreate: () -> Unit
) {
    composable(AuthorizedRoute.VendorRoute.CreatePackage.route) {
        ScreenCreatePackage (
            onBack = onBack,
            onCreate = onCreate
        )
    }
}

fun NavController.navigateToCreatePackage() {
    this.navigate(AuthorizedRoute.VendorRoute.CreatePackage.route)
}