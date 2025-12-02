package com.example.speediz.ui.feature.unauthorized.signup

sealed class SignUPState {
    object Idle : SignUPState()
    object Loading : SignUPState()
    data class Success( val message : String) : SignUPState()
    data class Error(val message: String) : SignUPState()
//    data class ValidationError(val message: String) : SignUPState()
//    data class EmailState(val email: String): SignUPState()
//    data class PasswordState(val password: String): SignUPState()
//    data class ConfirmPasswordState(val confirmPassword: String): SignUPState()
//    data class FirstnameState(val firstname: String): SignUPState()
//    data class LastnameState(val lastname: String): SignUPState()
//    data class DobState(val dob: String): SignUPState()
//    data class PhoneState(val phone: String): SignUPState()
//    data class AddressState(val address: String): SignUPState()
//    data class GenderState(val gender: String): SignUPState()
//    data class BusinessNameState(val businessName: String): SignUPState()

}