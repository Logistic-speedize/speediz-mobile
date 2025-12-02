package com.example.speediz.ui.feature.authorized.vendor.packageTracking.detail

import com.example.speediz.core.data.vendor.PackageTrackingDetailResponse

sealed class PackageUiState {
    object  Loading : PackageUiState()
    data class Success(val detail: PackageTrackingDetailResponse) : PackageUiState()
    data class Error(val message: String) : PackageUiState()
}