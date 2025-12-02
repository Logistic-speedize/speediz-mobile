package com.example.speediz.core.repository

import com.example.speediz.core.data.vendor.Vendor
import jakarta.inject.Inject

class VendorRepositoryImpl @Inject constructor(
) : VendorRepository {
    override suspend fun getCurrentVendor(): Vendor {
        // return api.getCurrentVendor()
        return Vendor(id = "15", email = "seavyong-vendor@gmail.com")
    }
}