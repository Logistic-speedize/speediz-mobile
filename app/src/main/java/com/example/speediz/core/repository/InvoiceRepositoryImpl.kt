package com.example.speediz.core.repository

import com.example.speediz.core.data.delivery.InvoiceDetailResponse
import com.example.speediz.core.data.delivery.InvoiceResponse
import com.example.speediz.core.network.SafeApiRequest
import com.example.speediz.core.network.services.ApiService
import javax.inject.Inject

class InvoiceRepositoryImpl @Inject constructor(
    private val api: ApiService
): InvoiceRepository, SafeApiRequest() {
    override suspend fun invoiceDelivery(): InvoiceResponse {
        return apiRequest {
            api.invoiceDelivery()
        }
    }

    override suspend fun invoiceDetail(id: Int): InvoiceDetailResponse {
        return apiRequest {
            api.invoiceDeliveryDetail(id)
        }
    }
}