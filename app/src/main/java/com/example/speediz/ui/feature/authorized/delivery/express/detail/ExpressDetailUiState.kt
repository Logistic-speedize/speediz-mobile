package com.example.speediz.ui.feature.authorized.delivery.express.detail

import com.example.speediz.core.data.model.ExpressDetailResponse

sealed class ExpressDetailUiState {
    object Loading : ExpressDetailUiState()
    data class Success(val detail: ExpressDetailResponse) : ExpressDetailUiState()
    data class Error(val message: String) : ExpressDetailUiState()
}