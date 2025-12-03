package com.example.speediz.ui.feature.authorized.delivery.account

import com.example.speediz.core.data.delivery.DeliveryProfileResponse

sealed class DeliveryProfileState {
    object Loading : DeliveryProfileState()
    data class Success(val response: DeliveryProfileResponse.Data) : DeliveryProfileState()
    data class Error(val message: String) : DeliveryProfileState()
}