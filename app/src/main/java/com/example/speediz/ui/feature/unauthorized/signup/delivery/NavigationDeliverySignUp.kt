package com.example.speediz.ui.feature.unauthorized.signup.delivery

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.speediz.ui.navigation.UnauthorizedRoute

fun NavGraphBuilder.screenDeliverySignup(
    onNavigateTo : () -> Unit,
    onBackPress : () -> Unit,
) {
    composable( route = UnauthorizedRoute.DeliverySignUp.route){
        ScreenDeliverySignUp(
            onNavigateTo = onNavigateTo ,
            onBackPress = onBackPress
        )
    }
}
fun NavController.navigateToDeliverySignUp(
    navOptions: NavOptions ? = null
) {
    this.navigate(UnauthorizedRoute.DeliverySignUp.route, navOptions)
}