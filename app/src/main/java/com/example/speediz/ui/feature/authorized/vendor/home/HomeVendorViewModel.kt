package com.example.speediz.ui.feature.authorized.vendor.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.repository.VendorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVendorViewModel @Inject constructor(
    private val repository: VendorRepository
    ) : ViewModel() {

        private val _uiState = MutableStateFlow(HomeVendorState())
        val uiState: MutableStateFlow<HomeVendorState> = _uiState

        init {
            loadVendor()
        }

        fun loadVendor() {
            Log.d("HomeVendor" , "Loading Vendor...")
            // avoid re-loading if already loaded
            if (!_uiState.value.isLoading && _uiState.value.email.isNotBlank()) return

            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true , error = null) }
                runCatching { repository.getCurrentVendor() }
                    .onSuccess { vendor ->
                        _uiState.update {
                            it.copy(
                                email = vendor.email,
                                profile = vendor.profile,
                                isLoading = false
                            )
                        }
                    }
                    .onFailure { e ->
                        _uiState.update { it.copy(isLoading = false, error = e.message ?: "Unknown error") }
                    }
            }
        }
}