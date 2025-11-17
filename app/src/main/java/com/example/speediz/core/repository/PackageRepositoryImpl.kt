package com.example.speediz.core.repository

import com.example.speediz.core.data.model.PackageTrackingDetailResponse
import com.example.speediz.core.network.SafeApiRequest
import com.example.speediz.core.network.services.ApiService
import javax.inject.Inject

class PackageRepositoryImpl @Inject constructor(
    private val api: ApiService
): SafeApiRequest(), PackageRepository {
    override suspend fun packageList() = apiRequest {
        api.packageList()
    }

    override suspend fun packageTrackingDetails(id : Int) : PackageTrackingDetailResponse {
        return apiRequest {
            api.packageTrackingDetail(id)
        }
    }

}