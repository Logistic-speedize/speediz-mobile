package com.example.speediz.ui.feature.authorized.vendor.invoiceManagement.invoiceList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.data.vendor.InvoiceResponse
import com.example.speediz.core.repository.InvoiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoiceListViewModel @Inject constructor(
    private val api: InvoiceRepository
): ViewModel() {

    private val _fullList = MutableStateFlow<List<InvoiceResponse.InvoiceItems>>(emptyList())

    private val _responseUIState = MutableStateFlow<List<InvoiceResponse.InvoiceItems>>(emptyList())
    val responseUIState get() = _responseUIState

    private val _loading = MutableStateFlow(false)
    val loading get() = _loading

    private val _dateFilter = MutableStateFlow("")
    val dateFilter get() = _dateFilter

    private val _searchQuery = MutableStateFlow("")
    val searchQuery get() = _searchQuery

    fun fetchInvoiceData() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = api.invoiceVendor()
                _fullList.value = response.data.invoiceItem
                _responseUIState.value = response.data.invoiceItem // default list

//                applyFilters()
            } catch (e: Exception) {

                Log.d("VendorInvoiceVM", "Error fetching invoice data: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }
}