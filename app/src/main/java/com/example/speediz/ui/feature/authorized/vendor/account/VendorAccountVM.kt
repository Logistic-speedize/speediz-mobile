package com.example.speediz.ui.feature.authorized.vendor.account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.repository.VendorProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VendorAccountVM @Inject constructor(
    private val repository: VendorProfileRepository
): ViewModel() {
    private var _profileUIState = MutableStateFlow<VendorProfileState>(VendorProfileState.Loading)
    val profileUIState get() = _profileUIState

    fun fetchProfileData() {
        viewModelScope.launch {
            try {
                val response = repository.getVendorProfile().data
                _profileUIState.value = VendorProfileState.Success(response)
                Log.d( "VendorAccountVM", "fetchProfileData: $response" )
            } catch (e: Exception) {
                Log.d( "VendorAccountVM", "fetchProfileData: ${e.message}" )
                _profileUIState.value = VendorProfileState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}