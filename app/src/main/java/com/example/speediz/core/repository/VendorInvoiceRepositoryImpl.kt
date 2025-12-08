package com.example.speediz.core.repository

import com.example.speediz.core.data.vendor.InvoiceDetailResponse
import com.example.speediz.core.data.vendor.InvoiceResponse
import com.example.speediz.core.network.SafeApiRequest
import com.example.speediz.core.network.services.ApiService
import javax.inject.Inject

class VendorInvoiceRepositoryImpl @Inject constructor(
    private val api: ApiService
): SafeApiRequest(), VendorInvoiceRepository {
    override suspend fun invoiceVendor(): InvoiceResponse {
        return apiRequest {
            api.invoiceVendor()
        }
    }

    override suspend fun invoiceVendorDetail(id: Int): InvoiceDetailResponse {
       return apiRequest {
           api.invoiceVendorDetail(id)
       }
    }
}