package com.example.speediz.ui.feature.authorized.delivery.express

import com.example.speediz.core.data.delivery.ExpressResponse

sealed class ExpressUiState {
    object Loading : ExpressUiState()
    data class Success(val data: Map<String, List<ExpressResponse.Data.ExpressItems>>) : ExpressUiState()
    data class Error(val message: String) : ExpressUiState()
}