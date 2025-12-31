package com.example.speediz.ui.feature.authorized.delivery.invoice

import com.example.speediz.core.data.delivery.InvoiceResponse

sealed class DeliveryInvoiceUiState {
    object Loading : DeliveryInvoiceUiState()
    data class Success(
        val invoiceUiState: List<InvoiceResponse.Data.InvoiceItems>
    ) : DeliveryInvoiceUiState()

    data class Error(
        val message: String
    ) : DeliveryInvoiceUiState()
}