package com.example.speediz.ui.feature.authorized.delivery.history

import com.example.speediz.core.data.model.delivery.PackageHistoryResponse

sealed class HistoryState{
    object Loading : HistoryState()
    data class Success(val items: List<PackageHistoryResponse.PackageHistoryData.PackageHistoryItem>) : HistoryState()
    data class Error(val message: String) : HistoryState()
}