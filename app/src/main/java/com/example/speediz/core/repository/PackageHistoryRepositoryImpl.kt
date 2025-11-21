package com.example.speediz.core.repository

import com.example.speediz.core.data.model.delivery.PackageHistoryDetailResponse
import com.example.speediz.core.data.model.delivery.PackageHistoryResponse
import com.example.speediz.core.network.SafeApiRequest
import com.example.speediz.core.network.services.ApiService
import javax.inject.Inject

class PackageHistoryRepositoryImpl @Inject constructor(
    private val api: ApiService
): PackageHistoryRepository, SafeApiRequest() {
    override suspend fun packageHistory(): PackageHistoryResponse {
        return apiRequest { api.packageHistory() }
    }

    override suspend fun packageHistoryDetail(id: Int): PackageHistoryDetailResponse {
        return apiRequest { api.packageHistoryDetail(id) }
    }
}