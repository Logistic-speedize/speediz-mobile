package com.example.speediz.core.repository

import com.example.speediz.core.data.model.Vendor

interface VendorRepository {
    suspend fun getCurrentVendor(): Vendor
}