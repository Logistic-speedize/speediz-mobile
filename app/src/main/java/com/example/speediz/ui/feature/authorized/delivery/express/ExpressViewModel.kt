package com.example.speediz.ui.feature.authorized.delivery.express

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.data.delivery.ExpressResponse
import com.example.speediz.core.repository.ExpressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpressViewModel @Inject constructor(
    val repository : ExpressRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<ExpressUiState>(ExpressUiState.Loading)
    val uiState = _uiState.asStateFlow()
    fun fetchExpressData(searchQuery: String = "") {
        viewModelScope.launch {
            try {
                val response = repository.express()
                val list = response.data.data
                val sortedDate = list.toSortedMap( compareByDescending { it }).mapValues { entry ->
                    entry.value.filter { item -> item.status != "completed"
                    }
                }
                val expressList = if (searchQuery.isNotEmpty()) {
                    sortedDate.mapValues { entry ->
                        entry.value.filter { item ->
                            item.id.toString().contains(searchQuery, ignoreCase = true)
                        }
                    }.filterValues { it.isNotEmpty() }
                } else {
                    sortedDate
                }
                _uiState.value = ExpressUiState.Success(expressList)
            } catch (e: Exception) {
            }
        }
    }

}