package com.example.speediz.ui.feature.authorized.delivery.history

import com.example.speediz.core.data.delivery.PackageHistoryResponse

sealed class HistoryState {
    object Loading : HistoryState()
    data class Success(
        val allItems: List<PackageHistoryResponse.PackageHistoryData.PackageHistoryItem>,
        val filteredItems: List<PackageHistoryResponse.PackageHistoryData.PackageHistoryItem>
    ) : HistoryState()

    object Empty : HistoryState()
    data class Error(val message: String) : HistoryState()
}