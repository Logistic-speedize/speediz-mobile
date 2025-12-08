// Kotlin
package com.example.speediz.ui.feature.authorized.vendor.invoiceManagement.invoiceList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.data.vendor.InvoiceResponse
import com.example.speediz.core.repository.VendorInvoiceRepository
import com.example.speediz.ui.utils.SpeedizPaidStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class PaymentFilter { ALL, PAID, UNPAID }

// Kotlin
@HiltViewModel
class InvoiceListViewModel @Inject constructor(
    private val api: VendorInvoiceRepository
): ViewModel() {
    private val _isInvoiceList = MutableStateFlow<List<InvoiceResponse.Data.VendorInvoices.InvoiceItems>>(emptyList())
    val isInvoiceList : StateFlow<List<InvoiceResponse.Data.VendorInvoices.InvoiceItems>> = _isInvoiceList.asStateFlow()

    private val _isQueryAllInvoice = MutableStateFlow("")
    val isQueryAllInvoice : StateFlow<String> = _isQueryAllInvoice.asStateFlow()

    private val _isQueryPaidInvoice = MutableStateFlow("")
    val isQueryPaidInvoice : StateFlow<String> = _isQueryPaidInvoice.asStateFlow()
    private val _isQueryUnpaidInvoice = MutableStateFlow("")
    val isQueryUnpaidInvoice : StateFlow<String> = _isQueryUnpaidInvoice.asStateFlow()
    fun fetchInvoiceList(){
        viewModelScope.launch {
            try {
                val response = api.invoiceVendor()
                _isInvoiceList.value = response.data.vendorInvoices.invoiceItem ?: emptyList()
                Log.d("InvoiceListViewModel" , "Fetched invoice data: ${_isInvoiceList.value} items" )
            } catch (e: Exception) {
                Log.d("InvoiceListViewModel" , "Error fetching invoice data: ${e.message}" )
            }
        }
    }
    fun searchInvoiceById(searchId: String, filter: PaymentFilter) {
        when (filter) {
            PaymentFilter.ALL -> {
                _isQueryAllInvoice.value = searchId
            }
            PaymentFilter.PAID -> {
                _isQueryPaidInvoice.value = searchId
            }
            PaymentFilter.UNPAID -> {
                _isQueryUnpaidInvoice.value = searchId
            }
        }
    }

    fun getFilteredInvoices(filter: PaymentFilter): List<InvoiceResponse.Data.VendorInvoices.InvoiceItems> {
        val allInvoices = _isInvoiceList.value

        // 1️⃣ Filter by Payment Status
        val byPayment = when (filter) {
            PaymentFilter.ALL -> allInvoices
            PaymentFilter.PAID -> allInvoices.filter {
                it.status.equals(SpeedizPaidStatus.Paid.status, ignoreCase = true)
            }
            PaymentFilter.UNPAID -> allInvoices.filter {
                it.status.equals(SpeedizPaidStatus.Unpaid.status, ignoreCase = true)
            }
        }

        // 2️⃣ Pick the correct searchQuery for the tab
        val searchQuery = when (filter) {
            PaymentFilter.ALL -> _isQueryAllInvoice.value
            PaymentFilter.PAID -> _isQueryPaidInvoice.value
            PaymentFilter.UNPAID -> _isQueryUnpaidInvoice.value
        }.trim()

        // 3️⃣ Filter by Invoice ID
        return if (searchQuery.isEmpty()) {
            byPayment
        } else {
            byPayment.filter { invoice ->
                (invoice.invoiceNumber ?: "").contains(searchQuery, ignoreCase = true)
            }
        }
    }

}

