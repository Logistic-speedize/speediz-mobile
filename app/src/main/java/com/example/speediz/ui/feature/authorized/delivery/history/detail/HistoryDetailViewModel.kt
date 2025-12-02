package com.example.speediz.ui.feature.authorized.delivery.history.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.data.delivery.PackageHistoryDetailResponse
import com.example.speediz.core.repository.PackageHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryDetailViewModel @Inject constructor(
    private val repository: PackageHistoryRepository
) : ViewModel() {
    private val _historyDetail = MutableStateFlow<PackageHistoryDetailResponse.PackageHistoryDetailData?>(null)
    val historyDetail = _historyDetail.asStateFlow()

    fun getHistoryDetailById(id: String){
        viewModelScope.launch {
            try {
                val response = repository.packageHistoryDetail(id = id.toInt())
                _historyDetail.value = response.data
            } catch (_: Exception) {
                Log.d ("HistoryDetailViewModel" , "Error fetching history detail for id: $id" )
            }
        }
    }
}