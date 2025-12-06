package com.example.speediz.core.repository

import com.example.speediz.core.data.vendor.VendorProfileResponse
import com.example.speediz.core.network.SafeApiRequest
import com.example.speediz.core.network.services.ApiService
import javax.inject.Inject

class VendorProfileRepositoryImpl @Inject constructor(
    private val api: ApiService
): SafeApiRequest() , VendorProfileRepository{
    override suspend fun getVendorProfile(): VendorProfileResponse {
        return apiRequest {
            api.getVendorProfile()
        }
    }
}