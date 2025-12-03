package com.example.speediz.ui.feature.authorized.delivery.account

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.speediz.ui.navigation.AuthorizedRoute

fun NavGraphBuilder.screenAccount(
    onLogOut: () -> Unit,
    onBack: () -> Unit,
) {
    composable(AuthorizedRoute.DeliveryRoute.Account.route) {
        ScreenAccount(
            onLogOut = onLogOut,
            onBackPress = onBack,
        )
    }
}
fun NavController.navigationAccount() {
    this.navigate( AuthorizedRoute.DeliveryRoute.Account.route)
}