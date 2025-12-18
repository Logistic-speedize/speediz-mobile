package com.example.speediz.core.repository

import com.example.speediz.core.data.ResponseErrorModel
import com.example.speediz.core.data.vendor.CreatePackageRequest
import com.example.speediz.core.data.vendor.PackageTrackingDetailResponse
import com.example.speediz.core.network.SafeApiRequest
import com.example.speediz.core.network.services.ApiService
import javax.inject.Inject

class PackageRepositoryImpl @Inject constructor(
    private val api: ApiService
): SafeApiRequest(), PackageRepository {
    override suspend fun packageList(page: Int) = apiRequest {
        api.packageList(page = page)
    }

    override suspend fun packageTrackingDetails(id : Int) : PackageTrackingDetailResponse {
        return apiRequest {
            api.packageTrackingDetail(id)
        }
    }

    override suspend fun createPackage(request: CreatePackageRequest): ResponseErrorModel {
        return apiRequest {
            api.createPackage(request)
        }
    }

}