package com.example.speediz.ui.feature.unauthorized.signIn

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.speediz.ui.navigation.UnauthorizedRoute

fun NavGraphBuilder.screenSignIn(
    onNavigateTo : (String) -> Unit ,
) {
    composable(route = UnauthorizedRoute.SignIn.route){
        ScreenSignIn(
            onNavigateTo = onNavigateTo
        )
    }
}
fun NavController.navigateToSignIn( navOptions: NavOptions ? = null) {
    this.navigate(UnauthorizedRoute.SignIn.route, navOptions)
}