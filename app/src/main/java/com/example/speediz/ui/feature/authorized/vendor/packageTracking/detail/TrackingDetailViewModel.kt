package com.example.speediz.ui.feature.authorized.vendor.packageTracking.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.data.vendor.PackageTrackingDetailResponse
import com.example.speediz.core.repository.PackageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TrackingDetailViewModel @Inject constructor(
    private  val repository: PackageRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<PackageUiState>(PackageUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _packageDetail = MutableStateFlow<PackageTrackingDetailResponse?>(null)
    val packageDetail = _packageDetail.asStateFlow()
    fun getPackageDetail(id: String) {
        _uiState.value = PackageUiState.Loading
        viewModelScope.launch {
            try {
                val response = repository.packageTrackingDetails(id.toInt())
                _packageDetail.value = response
                Log.d("TrackingDetailViewModel" , "Fetched package detail: $response")
                _uiState.value = PackageUiState.Success(response)
            } catch (e: Exception) {
                Log.d( "TrackingDetailViewModel" , "Error fetching package detail: ${e.message}" )
                _uiState.value = PackageUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}