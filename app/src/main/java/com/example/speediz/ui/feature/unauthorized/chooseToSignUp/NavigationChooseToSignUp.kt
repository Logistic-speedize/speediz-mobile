package com.example.speediz.ui.feature.unauthorized.chooseToSignUp

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.speediz.ui.navigation.UnauthorizedRoute

fun NavGraphBuilder.screenChooseToSignUp(
    onNavigateTo : ( String) -> Unit,
    onBackPress : () -> Unit
) {
    composable(UnauthorizedRoute.ChooseToSignUp.route){
        ScreenChooseToSignUp(
            onNavigateTo = onNavigateTo,
            onBackPress = onBackPress
        )
    }
}