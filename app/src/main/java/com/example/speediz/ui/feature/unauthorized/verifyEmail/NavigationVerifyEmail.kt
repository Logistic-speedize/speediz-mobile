package com.example.speediz.ui.feature.unauthorized.verifyEmail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.speediz.ui.navigation.UnauthorizedRoute

fun NavGraphBuilder.screenVerifyEmail() {
    composable(route = UnauthorizedRoute.VerifyEmail.route) {
       ScreenEmailVerify()
    }
}
fun NavController.navigateToVerifyEmail(
    navOptions: androidx.navigation.NavOptions? = null
) {
    this.navigate(UnauthorizedRoute.VerifyEmail.route, navOptions)
}