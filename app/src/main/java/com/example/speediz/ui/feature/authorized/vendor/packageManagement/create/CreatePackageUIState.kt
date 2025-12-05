package com.example.speediz.ui.feature.authorized.vendor.packageManagement.create

sealed class CreatePackageUIState {
    data object Loading : CreatePackageUIState()
    data object Success: CreatePackageUIState()
    data class Error(val message: String) : CreatePackageUIState()
}