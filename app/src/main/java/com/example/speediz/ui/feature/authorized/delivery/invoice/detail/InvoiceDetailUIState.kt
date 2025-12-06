package com.example.speediz.ui.feature.authorized.delivery.invoice.detail

import com.example.speediz.core.data.delivery.InvoiceDetailResponse

sealed class InvoiceDetailUIState {
    object Loading : InvoiceDetailUIState()
    data class Success(
        val invoiceDetailData: InvoiceDetailResponse.Data
    ) : InvoiceDetailUIState()
    data class Error(val message: String) : InvoiceDetailUIState()
}