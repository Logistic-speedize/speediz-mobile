package com.example.speediz.ui.feature.authorized.delivery.express

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.data.model.ExpressResponse
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
    private val _expressList = MutableStateFlow<Map<String, List<ExpressResponse.Data.ExpressItems>>>(emptyMap())
    private val _expressDate = MutableStateFlow("")

    private val _expressFilter = MutableStateFlow<Map<String, List<ExpressResponse.Data.ExpressItems>>>(emptyMap())
    val expressFilter = _expressFilter.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    init {
        fetchExpressData()
        expressFilter
    }
    fun fetchExpressData() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.express()
                val list = response.data.data
                val sortedDate = list.toSortedMap( compareByDescending { it })
                _expressDate.value = response.data.data.keys.firstOrNull() ?: ""
                _expressList.value = sortedDate
                _expressFilter.value = sortedDate
                Log.d("ExpressViewModel" , "Fetched express data: ${_expressList.value} items" )
            } catch (e: Exception) {
                Log.d("ExpressViewModel" , "Error fetching express data: ${e.message}" )
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchExpressById(searchId: String) {

        if ( searchId.isBlank()){
            _expressFilter.value = _expressList.value
        } else {
            val filtered = _expressList.value.mapValues { entry ->
                entry.value.filter {
                    it.id.toString().contains( searchId, ignoreCase = true)
                }
            }.filterValues {
                it.isNotEmpty()
            }
            _expressFilter.value = filtered
        }

    }
}