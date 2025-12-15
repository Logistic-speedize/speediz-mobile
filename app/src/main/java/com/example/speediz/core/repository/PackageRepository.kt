package com.example.speediz.core.repository

import com.example.speediz.core.data.ResponseErrorModel
import com.example.speediz.core.data.vendor.CreatePackageRequest
import com.example.speediz.core.data.vendor.PackageResponse
import com.example.speediz.core.data.vendor.PackageTrackingDetailResponse

interface PackageRepository {
    suspend fun packageList(page: Int): PackageResponse
    suspend fun packageTrackingDetails(id: Int): PackageTrackingDetailResponse
    suspend fun createPackage(request: CreatePackageRequest): ResponseErrorModel
}