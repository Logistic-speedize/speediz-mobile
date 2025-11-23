package com.example.speediz.core.repository

import com.example.speediz.core.data.vendor.PackageResponse
import com.example.speediz.core.data.vendor.PackageTrackingDetailResponse

interface PackageRepository {
    suspend fun packageList(): PackageResponse
    suspend fun packageTrackingDetails(id: Int): PackageTrackingDetailResponse
}