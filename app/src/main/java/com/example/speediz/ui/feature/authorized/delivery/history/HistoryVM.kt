package com.example.speediz.ui.feature.authorized.delivery.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.data.delivery.PackageHistoryResponse
import com.example.speediz.core.repository.PackageHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryVM @Inject constructor(
    private val historyRepository: PackageHistoryRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<HistoryState>(HistoryState.Loading)
    val uiState = _uiState.asStateFlow()

    private var allHistoryItems: List<PackageHistoryResponse.PackageHistoryData.PackageHistoryItem> = emptyList()

    init {
        fetchHistory()
    }

    private fun fetchHistory() {
        viewModelScope.launch {
            try {
                _uiState.value = HistoryState.Loading
                val response = historyRepository.packageHistory()
                allHistoryItems = response.data.historyItems

                if (allHistoryItems.isEmpty()) {
                    _uiState.value = HistoryState.Empty
                } else {
                    _uiState.value = HistoryState.Success(
                        allItems = allHistoryItems,
                        filteredItems = allHistoryItems
                    )
                }
            } catch (e: Exception) {
                _uiState.value = HistoryState.Error(e.localizedMessage ?: "Something went wrong")
            }
        }
    }

    // Filter by status and/or date
    fun filterPackageHistory(status: String? = null, date: String? = null) {
       viewModelScope.launch {
           val filteredList = allHistoryItems.filter { item ->
               val matchStatus = status?.let { it.equals(item.status, ignoreCase = true) } ?: true
               val matchDate = date?.let { item.shipmentInfo.date.startsWith(it.take(10)) } ?: true
               matchStatus && matchDate
           }

           _uiState.value = if (filteredList.isEmpty()) {
               HistoryState.Empty
           } else {
               HistoryState.Success(
                   allItems = allHistoryItems,
                   filteredItems = filteredList
               )
           }
       }
    }
}
