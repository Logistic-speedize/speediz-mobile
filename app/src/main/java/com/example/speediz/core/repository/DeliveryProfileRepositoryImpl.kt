package com.example.speediz.core.repository

import com.example.speediz.core.data.delivery.DeliveryProfileResponse
import com.example.speediz.core.network.SafeApiRequest
import com.example.speediz.core.network.services.ApiService
import javax.inject.Inject

class DeliveryProfileRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): SafeApiRequest(), DeliveryProfileRepository {
    override suspend fun getDeliveryProfile(): DeliveryProfileResponse {
        return apiRequest {
            apiService.getDeliveryProfile()
        }
    }
}