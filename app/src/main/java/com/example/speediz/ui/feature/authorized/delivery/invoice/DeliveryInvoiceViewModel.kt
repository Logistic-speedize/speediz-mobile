package com.example.speediz.ui.feature.authorized.delivery.invoice

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.data.delivery.InvoiceResponse
import com.example.speediz.core.repository.InvoiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class DeliveryInvoiceViewModel @Inject constructor(
    private val api: InvoiceRepository
): ViewModel() {
    private val _responseUIState = MutableStateFlow<List<InvoiceResponse.Data.InvoiceItems>>(emptyList())
    val responseUIState get() = _responseUIState

    private val _loading = MutableStateFlow(false)
    val loading get() = _loading

    fun fetchInvoiceData() {
        _loading.value = true
       viewModelScope.launch {
              try {
                val response = api.invoiceDelivery()
                _responseUIState.value = response.data.invoiceItem
              } catch (e: Exception) {
               Log.d( "DeliveryInvoiceVM" , "Error fetching invoice data: ${e.message}" )
              } finally {
                _loading.value = false
              }
       }
    }
}