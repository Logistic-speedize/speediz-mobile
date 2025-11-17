package com.example.speediz.core.repository

import com.example.speediz.core.data.model.PackageResponse
import com.example.speediz.core.data.model.PackageTrackingDetailResponse
import okhttp3.Response

interface PackageRepository {
    suspend fun packageList(): PackageResponse
    suspend fun packageTrackingDetails(id: Int): PackageTrackingDetailResponse
}