package com.example.speediz.ui.feature.unauthorized.signup

sealed class DeliverySignUpUiState {
    object Idle : DeliverySignUpUiState()
    object Loading : DeliverySignUpUiState()
    data class Success(val message: String) : DeliverySignUpUiState()
    data class Error(val errorMessage: String) : DeliverySignUpUiState()
}