package com.example.speediz.ui.feature.unauthorized.signup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.speediz.ui.navigation.UnauthorizedRoute

fun NavGraphBuilder.screenSignup(
    onNavigateTo : () -> Unit,
    onBackPress : () -> Unit,
) {
    composable( route = UnauthorizedRoute.SignUp.route){
        ScreenSignUp(
            onNavigateTo = onNavigateTo,
            onBackPress = onBackPress
        )
    }
}
fun NavController.navigateToSignUp(
    navOptions: androidx.navigation.NavOptions ? = null
) {
    this.navigate(UnauthorizedRoute.SignUp.route, navOptions)
}