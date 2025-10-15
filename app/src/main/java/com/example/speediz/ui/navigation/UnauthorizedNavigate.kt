package com.example.speediz.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.speediz.MainViewModel
import com.example.speediz.ui.feature.unauthorized.forgotPassword.screenForgotPassword
import com.example.speediz.ui.feature.unauthorized.signIn.ScreenSignIn
import com.example.speediz.ui.feature.unauthorized.signIn.screenSignIn
import com.example.speediz.ui.feature.unauthorized.signup.screenSignup
import com.example.speediz.ui.feature.unauthorized.verifyEmail.screenVerifyEmail
import com.example.speediz.ui.graphs.Graph

fun NavGraphBuilder.unauthorizedNavigate(
    navController: NavHostController
) {
    screenSignIn(
        onNavigateTo = {
            navController.navigate(UnauthorizedRoute.SignIn.route)
        }
    )
    screenSignup(
        onNavigateTo = {
            navController.navigate(UnauthorizedRoute.SignUp.route)
        },
        onBackPress = {
            navController.popBackStack()
        }
    )
    screenForgotPassword(
        onNavigateTo = {
            navController.navigate(UnauthorizedRoute.SignIn.route)
        },
        onBackPress = {
            navController.popBackStack()
        }
    )
    screenVerifyEmail()
}