package com.example.speediz.ui.navigation

sealed class UnauthorizedRoute(val route : String) {
    data object Main : UnauthorizedRoute(route = "main")
    data object Onboarding : UnauthorizedRoute(route = "onboarding")
    data object SignIn : UnauthorizedRoute(route = "sign_in")
    data object DeliverySignUp : UnauthorizedRoute(route = "delivery_sign_up")
    data object VendorSignUp : UnauthorizedRoute(route = "vendor_sign_up")
    data object ChooseToSignUp : UnauthorizedRoute(route = "choose_to_sign_up")
    data object ForgotPassword : UnauthorizedRoute(route = "forgot_password")
    data object VerifyEmail : UnauthorizedRoute(route = "verify_email")
}