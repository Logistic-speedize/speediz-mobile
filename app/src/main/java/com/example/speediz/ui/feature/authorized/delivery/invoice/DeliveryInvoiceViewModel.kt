package com.example.speediz.ui.feature.authorized.delivery.invoice

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.repository.InvoiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveryInvoiceViewModel @Inject constructor(
    private val api: InvoiceRepository
): ViewModel() {

    private val _invoiceUIState = MutableStateFlow<DeliveryInvoiceUiState>(DeliveryInvoiceUiState.Loading)
    val invoiceUIState  get() = _invoiceUIState

    fun fetchInvoiceData(searchQuery: String ?= null, date: String ?= null) {
        viewModelScope.launch {
           val response = api.invoiceDelivery()
              val invoiceList = response.data.invoiceItem.filter { items ->
                    val matchQuery = searchQuery?.let {
                        items.invoiceNumber.contains(it, ignoreCase = true)
                    } ?: true

                    val matchDate = date?.let {
                        val formattedDate =date.let { items.invoiceDate }
                        formattedDate.startsWith(it.take(10))
                    } ?: true
                    matchQuery && matchDate
              }
            Log.d( "dateFilter", "fetchInvoiceData: $invoiceList")
            _invoiceUIState.value = if (invoiceList.isNotEmpty()) {
                DeliveryInvoiceUiState.Success(invoiceUiState = invoiceList)
            } else {
                DeliveryInvoiceUiState.Success(invoiceUiState = emptyList())
            }
        }
    }

}