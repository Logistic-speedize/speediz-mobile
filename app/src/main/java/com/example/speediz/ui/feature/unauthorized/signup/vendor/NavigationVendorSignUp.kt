package com.example.speediz.ui.feature.unauthorized.signup.vendor

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.speediz.ui.navigation.UnauthorizedRoute


fun NavGraphBuilder.screenVendorSignUp(
    onNavigateTo: (String) -> Unit,
    onBackPress: () -> Unit
) {
    composable(UnauthorizedRoute.VendorSignUp.route){
        ScreenVendorSignUp(
            onNavigateTo = onNavigateTo,
            onBackPress = onBackPress
        )
    }
}

fun NavController.navigateToVendorSignUp() {
    this.navigate(UnauthorizedRoute.VendorSignUp.route)
}