package com.example.speediz.ui.feature.authorized.vendor.account

import com.example.speediz.core.data.vendor.VendorProfileResponse

sealed class VendorProfileState {
    object Loading : VendorProfileState()
    data class Success(val response: VendorProfileResponse.Data) : VendorProfileState()
    data class Error(val message: String) : VendorProfileState()
}