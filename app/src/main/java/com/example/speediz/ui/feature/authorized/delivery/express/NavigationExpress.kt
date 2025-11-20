package com.example.speediz.ui.feature.authorized.delivery.express

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.speediz.ui.navigation.AuthorizedRoute

fun NavGraphBuilder.screenExpress(
    navigateTo: (String) -> Unit,
    onBack: () -> Unit
) {
    composable(AuthorizedRoute.DeliveryRoute.Express.route){
        ScreenExpress(
            onNavigateTo = navigateTo,
            onBack = onBack
        )
    }
}