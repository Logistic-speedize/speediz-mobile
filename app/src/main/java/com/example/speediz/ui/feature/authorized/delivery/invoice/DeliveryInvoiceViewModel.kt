package com.example.speediz.ui.feature.authorized.delivery.invoice

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.data.delivery.InvoiceResponse
import com.example.speediz.core.repository.InvoiceRepository
import com.example.speediz.ui.utils.dateFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class DeliveryInvoiceViewModel @Inject constructor(
    private val api: InvoiceRepository
): ViewModel() {

    private val _fullList = MutableStateFlow<List<InvoiceResponse.Data.InvoiceItems>>(emptyList())

    private val _responseUIState = MutableStateFlow<List<InvoiceResponse.Data.InvoiceItems>>(emptyList())
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
                val response = api.invoiceDelivery()
                _fullList.value = response.data.invoiceItem        // store original data
                _responseUIState.value = response.data.invoiceItem // default list

                applyFilters()
            } catch (e: Exception) {
                Log.d("DeliveryInvoiceVM", "Error fetching invoice data: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }


    fun filterByDate(date: String) {
        _dateFilter.value = date
        applyFilters()
        Log.d( "DeliveryInvoiceVM", "Date filter applied: $date" )
    }

    fun filterByQuery(query: String) {
        _searchQuery.value = query
        applyFilters()
    }

    private fun applyFilters() {
        val date = _dateFilter.value
        val query = _searchQuery.value.trim().lowercase()

        viewModelScope.launch {
            val filteredList = _fullList.value.filter { item ->

                // Safe date extraction
                val invoiceDate = item.invoiceDate.split("T").getOrNull(0) ?: ""

                val matchesDate = if (date.isNotEmpty()) {
                    invoiceDate == date
                } else true

                Log.d("DeliveryInvoiceVM", "Item Date: $invoiceDate, Matches Date: $matchesDate")

                val matchesQuery = if (query.isNotEmpty()) {
                    item.id.toString().lowercase().contains(query) ||
                            item.invoiceNumber.lowercase().contains(query)
                } else true

                matchesDate && matchesQuery
            }

            _responseUIState.value = filteredList
        }
    }

}