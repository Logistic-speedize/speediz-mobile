package com.example.speediz.ui.feature.authorized.delivery.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.data.model.delivery.PackageHistoryResponse
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

    private val _historyList = MutableStateFlow<List<PackageHistoryResponse.PackageHistoryData.PackageHistoryItem>>(emptyList())
    val historyList = _historyList.asStateFlow()

    private val _historyFilterList = MutableStateFlow<List<PackageHistoryResponse.PackageHistoryData.PackageHistoryItem>>(emptyList())
    val historyFilterList = _historyFilterList.asStateFlow()

    private val _historyFilterByDate = MutableStateFlow<List<PackageHistoryResponse.PackageHistoryData.PackageHistoryItem>>(emptyList())
    val historyFilterByDate = _historyFilterByDate.asStateFlow()

    private val _statusList = MutableStateFlow<List<String>>(emptyList())
    val statusList = _statusList.asStateFlow()


//    fun fetchPackageHistory() {
//        _uiState.value = HistoryState.Loading
//        viewModelScope.launch {
//            try {
//                val response = historyRepository.packageHistory()
//                val list = response.data.historyItems
//                Log.d( "HistoryVM" , "Fetched package history: ${response} items" )
//                _historyList.value = list
//
//                _statusList.value = list.map { it.status }
//                    .distinct()
//                    .map { it.replaceFirstChar { c -> c.uppercase() } }
//
//            } catch (_: Exception) {
//            }
//        }
//    }

    fun fetchPackageHistoryByStatus(status: String ?= null, date: String ?= null) {
        viewModelScope.launch {
            try {
                val response = historyRepository.packageHistory()
                val list = response.data.historyItems
                _historyFilterList.value =
                   when {
                    !status.isNullOrEmpty() -> {
                        list.filter { it.status.equals(status, ignoreCase = true) }
                    }
                    !date.isNullOrEmpty() -> {
                        list.filter { it.shipmentInfo.date.contains(date) }
                    }
                    else -> {
                        list
                    }
                   }

            } catch (_: Exception) {}
        }
    }


}