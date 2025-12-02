package com.example.speediz.core.repository

import com.example.speediz.core.data.delivery.PackageHistoryDetailResponse
import com.example.speediz.core.data.delivery.PackageHistoryResponse

interface PackageHistoryRepository {
    suspend fun packageHistory(): PackageHistoryResponse
    suspend fun packageHistoryDetail(id: Int): PackageHistoryDetailResponse
}