package com.example.speediz.core.repository

import com.example.speediz.core.data.vendor.VendorProfileResponse

interface VendorProfileRepository {
    suspend fun getVendorProfile(): VendorProfileResponse
}