package com.example.speediz.core.repository

import com.example.speediz.core.data.model.ExpressResponse
import com.example.speediz.core.network.SafeApiRequest
import com.example.speediz.core.network.services.ApiService
import javax.inject.Inject

class ExpressRepositoryImpl @Inject constructor(
    private val api: ApiService

) : ExpressRepository, SafeApiRequest() {
    override suspend fun express() : ExpressResponse {
        return apiRequest {
            api.deliveryExpress()
        }
    }
}