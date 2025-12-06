package com.example.speediz.ui.feature.authorized.delivery.account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.repository.DeliveryProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountVM @Inject constructor(
    private val repository: DeliveryProfileRepository
): ViewModel() {
    private var _profileUIState = MutableStateFlow<DeliveryProfileState>(DeliveryProfileState.Loading)
    val profileUIState get() = _profileUIState

    fun fetchProfileData() {
        viewModelScope.launch {
            try {
                val response = repository.getDeliveryProfile().data
                _profileUIState.value = DeliveryProfileState.Success(response)
            } catch (e: Exception) {
                _profileUIState.value = DeliveryProfileState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}