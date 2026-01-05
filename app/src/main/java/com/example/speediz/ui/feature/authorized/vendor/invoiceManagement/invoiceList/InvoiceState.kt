package com.example.speediz.ui.feature.authorized.vendor.invoiceManagement.invoiceList

import com.example.speediz.core.data.vendor.InvoiceResponse

sealed class InvoiceUiState {
    object Loading : InvoiceUiState()
    data class Success(val data: List<InvoiceResponse.Data.VendorInvoices.InvoiceItems>) : InvoiceUiState()
    data class Error(val message: String) : InvoiceUiState()
}