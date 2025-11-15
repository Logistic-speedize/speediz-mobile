package com.example.speediz.ui.feature.authorized.vendor.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.speediz.ui.navigation.AuthorizedRoute

fun NavGraphBuilder.screenHomeVendor(
    onNavigateTo : (String) -> Unit
){
    composable(AuthorizedRoute.VendorRoute.Home.route) {
        ScreenHomeVendor(
            onNavigateTo = onNavigateTo
        )
    }
}
fun NavController.navigateToHomeVendor(){
    this.navigate(AuthorizedRoute.VendorRoute.Home.route)
}