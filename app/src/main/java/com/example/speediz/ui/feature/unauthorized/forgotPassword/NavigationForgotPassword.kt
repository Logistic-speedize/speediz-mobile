package com.example.speediz.ui.feature.unauthorized.forgotPassword

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.speediz.ui.navigation.UnauthorizedRoute

fun NavGraphBuilder.screenForgotPassword(
    onNavigateTo : () -> Unit = {},
    onBackPress : () -> Unit = {},
) {
   composable( route = UnauthorizedRoute.ForgotPassword.route){
         ScreenForgotPassword(
              onNavigateTo = onNavigateTo,
              onBackPress = onBackPress
         )
   }
}
fun NavController.navigateToForgotPassword(
    navOptions: NavOptions ? = null
) {
    this.navigate(UnauthorizedRoute.ForgotPassword.route, navOptions)
}
