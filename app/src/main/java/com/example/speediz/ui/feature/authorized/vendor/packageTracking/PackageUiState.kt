package com.example.speediz.ui.feature.authorized.vendor.packageTracking

import com.example.speediz.core.data.vendor.PackageResponse

sealed class PackageUiState {
    object Loading : PackageUiState()
    data class Success(val data: List<PackageResponse.DataPackage.PackageItem>) : PackageUiState()
    data class Error(val message: String) : PackageUiState()
}