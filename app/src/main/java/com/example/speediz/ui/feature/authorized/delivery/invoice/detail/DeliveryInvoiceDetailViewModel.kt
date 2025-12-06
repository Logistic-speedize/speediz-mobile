package com.example.speediz.ui.feature.authorized.delivery.invoice.detail

import androidx.lifecycle.ViewModel
import com.example.speediz.core.repository.InvoiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class DeliveryInvoiceDetailViewModel @Inject constructor(
    private val repository: InvoiceRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<InvoiceDetailUIState>(InvoiceDetailUIState.Loading)
    val uiState  get() = _uiState

    suspend fun getInvoiceDetailById(id: String) {
        try {
            val response = repository.invoiceDetail(id.toInt())
            _uiState.value = InvoiceDetailUIState.Success(invoiceDetailData = response.data)
        } catch (e: Exception) {
            _uiState.value = InvoiceDetailUIState.Error(e.message ?: "Unknown Error")
        }
    }
}