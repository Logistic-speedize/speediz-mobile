package com.example.speediz.ui.feature.authorized.vendor.invoiceManagement.detail

import com.example.speediz.core.data.vendor.InvoiceDetailResponse

sealed class VendorInvoiceDetailUIState {
    object Loading : VendorInvoiceDetailUIState()
    data class Success(
        val invoiceDetailData: InvoiceDetailResponse.InvoiceDetail
    ) : VendorInvoiceDetailUIState()
    data class Error(val message: String) : VendorInvoiceDetailUIState()
}