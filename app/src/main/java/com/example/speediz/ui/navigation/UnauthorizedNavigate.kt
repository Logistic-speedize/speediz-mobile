package com.example.speediz.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.example.speediz.ui.feature.unauthorized.chooseToSignUp.screenChooseToSignUp
import com.example.speediz.ui.feature.unauthorized.forgotPassword.screenForgotPassword
import com.example.speediz.ui.feature.unauthorized.signIn.screenSignIn
import com.example.speediz.ui.feature.unauthorized.signup.delivery.screenDeliverySignup
import com.example.speediz.ui.feature.unauthorized.signup.vendor.screenVendorSignUp
import com.example.speediz.ui.feature.unauthorized.verifyEmail.screenVerifyEmail

fun NavGraphBuilder.unauthorizedNavigate(
    navController: NavHostController
) {
    screenSignIn(
        onNavigateTo = { route ->
            navController.navigate(route)
        }
    )
    screenChooseToSignUp(
        onNavigateTo = { route ->
            navController.navigate(route)
        },
        onBackPress = {
            navController.popBackStack()
        }
    )
    screenDeliverySignup(
        onNavigateTo = {
            navController.navigate(UnauthorizedRoute.SignIn.route)
        } ,
        onBackPress = {
            navController.popBackStack()
        }
    )
    screenVendorSignUp(
        onNavigateTo = {
            navController.navigate(UnauthorizedRoute.SignIn.route)
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