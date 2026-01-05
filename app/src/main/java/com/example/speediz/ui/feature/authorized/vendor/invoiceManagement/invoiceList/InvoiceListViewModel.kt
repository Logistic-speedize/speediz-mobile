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
    private val _isInvoiceList = MutableStateFlow<InvoiceUiState>(InvoiceUiState.Loading)
    val isInvoiceUiState : StateFlow<InvoiceUiState> = _isInvoiceList.asStateFlow()
    private val _isQueryAllInvoice = MutableStateFlow("")
    val isQueryAllInvoice : StateFlow<String> = _isQueryAllInvoice.asStateFlow()

    private val _isQueryPaidInvoice = MutableStateFlow("")
    val isQueryPaidInvoice : StateFlow<String> = _isQueryPaidInvoice.asStateFlow()
    private val _isQueryUnpaidInvoice = MutableStateFlow("")
    val isQueryUnpaidInvoice : StateFlow<String> = _isQueryUnpaidInvoice.asStateFlow()

    // kotlin
    fun fetchAndFilterInvoices(
        searchId: String = "",
        filter: PaymentFilter = PaymentFilter.ALL,
    ) {
        viewModelScope.launch {
            try {
                val response = api.invoiceVendor()
                val fullList = response.data.vendorInvoices.invoiceItem ?: emptyList()

                // store the search query for the active tab
                when (filter) {
                    PaymentFilter.ALL -> _isQueryAllInvoice.value = searchId
                    PaymentFilter.PAID -> _isQueryPaidInvoice.value = searchId
                    PaymentFilter.UNPAID -> _isQueryUnpaidInvoice.value = searchId
                }

                // 1) Filter by Payment Status
                val byPayment = when (filter) {
                    PaymentFilter.ALL -> fullList
                    PaymentFilter.PAID -> fullList.filter {
                        it.status.equals(SpeedizPaidStatus.Paid.status, ignoreCase = true)
                    }
                    PaymentFilter.UNPAID -> fullList.filter {
                        it.status.equals(SpeedizPaidStatus.Unpaid.status, ignoreCase = true)
                    }
                }

                // 2) pick saved search query for the tab and trim
                val searchQuery = when (filter) {
                    PaymentFilter.ALL -> _isQueryAllInvoice.value
                    PaymentFilter.PAID -> _isQueryPaidInvoice.value
                    PaymentFilter.UNPAID -> _isQueryUnpaidInvoice.value
                }.trim()

                // 3) Filter by invoiceNumber
                val filtered = if (searchQuery.isEmpty()) {
                    byPayment
                } else {
                    byPayment.filter { invoice ->
                        (invoice.invoiceNumber ?: "").contains(searchQuery, ignoreCase = true)
                    }
                }
                _isInvoiceList.value = InvoiceUiState.Success(filtered)
            } catch (e: Exception) {
                Log.d("InvoiceListVM", "Error fetching invoices: ${e.message}")
            }
        }
    }

}

