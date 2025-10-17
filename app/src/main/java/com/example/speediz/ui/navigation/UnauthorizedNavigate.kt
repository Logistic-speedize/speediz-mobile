package com.example.speediz.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.example.speediz.ui.feature.unauthorized.forgotPassword.screenForgotPassword
import com.example.speediz.ui.feature.unauthorized.signIn.screenSignIn
import com.example.speediz.ui.feature.unauthorized.signup.screenSignup
import com.example.speediz.ui.feature.unauthorized.verifyEmail.screenVerifyEmail

fun NavGraphBuilder.unauthorizedNavigate(
    navController: NavHostController
) {
    screenSignIn(
        onNavigateTo = { route ->
            navController.navigate(route)
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