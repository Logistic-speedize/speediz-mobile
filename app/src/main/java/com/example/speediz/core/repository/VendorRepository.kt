package com.example.speediz.core.repository

import com.example.speediz.core.data.vendor.Vendor

interface VendorRepository {
    suspend fun getCurrentVendor(): Vendor
}