package com.example.speediz.ui.feature.authorized.delivery.express.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.data.model.ExpressDetailResponse
import com.example.speediz.core.repository.ExpressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpressDetailViewModel @Inject constructor(
    private val repository : ExpressRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<ExpressDetailUiState>(ExpressDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _expressDetail = MutableStateFlow<ExpressDetailResponse?>(null)
    val expressDetail = _expressDetail.asStateFlow()

    fun getExpressDetail(id: Int) {
       viewModelScope.launch {
           _uiState.value = ExpressDetailUiState.Loading

           val response = repository.expressDetail(id)
           try {
                _uiState.value = ExpressDetailUiState.Success(response)
                _expressDetail.value = response
              } catch (e: Exception) {
                _uiState.value = ExpressDetailUiState.Error(e.message ?: "Unknown Error")
           }
       }
    }
}