package com.example.speediz.core.repository

import com.example.speediz.core.data.delivery.DeliveryProfileResponse

interface DeliveryProfileRepository {
    suspend fun getDeliveryProfile(): DeliveryProfileResponse
}