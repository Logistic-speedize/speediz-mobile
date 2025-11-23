package com.example.speediz.ui.feature.authorized.vendor.packageManagement.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.data.vendor.PackageTrackingDetailResponse
import com.example.speediz.core.repository.PackageRepository
import com.example.speediz.ui.feature.authorized.vendor.packageTracking.detail.PackageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PackageDetailViewModel @Inject constructor(
    private val repository: PackageRepository
): ViewModel() {
    private val _packageUiState = MutableStateFlow<PackageUiState>(PackageUiState.Loading)
    val packageUiState = _packageUiState.asStateFlow()

    private val _packageDetail = MutableStateFlow<PackageTrackingDetailResponse?>(null)
    val packageDetail = _packageDetail.asStateFlow()

   fun fetchPackageDetail(id: String) {
        _packageUiState.value = PackageUiState.Loading
        viewModelScope.launch {
            try {
                val response = repository.packageTrackingDetails(id.toInt())
                _packageDetail.value = response
                Log.d("TrackingDetailViewModel" , "Fetched package detail: $response")
                _packageUiState.value = PackageUiState.Success(response)
            } catch (e: Exception) {
                Log.d( "TrackingDetailViewModel" , "Error fetching package detail: ${e.message}" )
                _packageUiState.value = PackageUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}