package com.example.speediz.core.repository

interface VendorInvoiceRepository {
    suspend fun invoiceVendor(): com.example.speediz.core.data.vendor.InvoiceResponse

    suspend fun invoiceVendorDetail(id: Int): com.example.speediz.core.data.vendor.InvoiceDetailResponse
}