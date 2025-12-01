package com.example.speediz.core.repository

import com.example.speediz.core.data.delivery.InvoiceDetailResponse
import com.example.speediz.core.data.delivery.InvoiceResponse

interface InvoiceRepository {
    suspend fun invoiceDelivery(): InvoiceResponse

    suspend fun invoiceDetail(id: Int): InvoiceDetailResponse
}