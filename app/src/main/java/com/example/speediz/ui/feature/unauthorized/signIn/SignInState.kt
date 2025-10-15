package com.example.speediz.ui.feature.unauthorized.signIn

sealed class SignInState {
    object Idle : SignInState()
    object Loading : SignInState()
    data class Success(val token: String) : SignInState()
    data class Error(val message: String) : SignInState()
    data class ValidationError(val emailError: String?, val passwordError: String?) : SignInState()
    object VendorRoute : SignInState()
    object DeliveryRoute : SignInState()
}