package com.example.speediz.ui.feature.authorized.vendor.invoiceManagement.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.speediz.core.repository.InvoiceRepository
import com.example.speediz.core.repository.VendorInvoiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class VendorInvoiceDetailVM @Inject constructor(
    private val repository: VendorInvoiceRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<VendorInvoiceDetailUIState>(VendorInvoiceDetailUIState.Loading)
    val uiState  get() = _uiState

    suspend fun getInvoiceDetailById(id: String) {
        try {
            val response = repository.invoiceVendorDetail(id.toInt())
            Log.d( "VendorInvoiceDetailVM", "getInvoiceDetailById: ${response.invoiceDetail}")
            _uiState.value = VendorInvoiceDetailUIState.Success(invoiceDetailData = response.invoiceDetail)
        } catch (e: Exception) {
            _uiState.value = VendorInvoiceDetailUIState.Error(e.message ?: "Unknown Error")
        }
    }
}