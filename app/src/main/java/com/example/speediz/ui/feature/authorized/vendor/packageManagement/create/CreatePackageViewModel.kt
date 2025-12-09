// kotlin
package com.example.speediz.ui.feature.authorized.vendor.packageManagement.create

import android.text.style.TtsSpan
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.data.vendor.CreatePackageRequest
import com.example.speediz.core.repository.PackageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePackageViewModel @Inject constructor(
    private val repository: PackageRepository
) : ViewModel() {

    private val _createPackageUIState = MutableStateFlow<CreatePackageUIState>(CreatePackageUIState.Loading)
    val createPackageUIState: StateFlow<CreatePackageUIState> = _createPackageUIState.asStateFlow()

    private val _receiverPhone = MutableStateFlow("")
    var receiverPhone: StateFlow<String> = _receiverPhone.asStateFlow()

    private val _receiverName = MutableStateFlow("")
    var receiverName: StateFlow<String> = _receiverName.asStateFlow()

    private val _packageType = MutableStateFlow("")
    var packageType: StateFlow<String> = _packageType.asStateFlow()

    private val _packagePrice = MutableStateFlow(0.0)
    var packagePrice: StateFlow<Double> = _packagePrice.asStateFlow()

    private val _customerLocation = MutableStateFlow("")
    var customerLocation: StateFlow<String> = _customerLocation.asStateFlow()

    private val _receiverLat = MutableStateFlow(0.0)
    var receiverLat: StateFlow<Double> = _receiverLat.asStateFlow()

    private val _receiverLng = MutableStateFlow(0.0)
    var receiverLng: StateFlow<Double> = _receiverLng.asStateFlow()

    // Update methods called by UI to set values before validation
    fun setReceiverPhone(value: String): String {
        _receiverPhone.value = value
        var message = ""
        when {
            value.isBlank() -> {
                message = "Phone number cannot be empty"
            }
            value.length !in 6..<15 -> {
                message = "Phone number must be between 6 and 14 digits"
            }
        }
        return message
    }
    fun setReceiverName(value: String): String {
        _receiverName.value = value
        val onlyLettersRegex = Regex("^[A-Za-z\\s]+$")
        var message = ""
        when {
            value.isBlank() -> {
                message = "Name cannot be empty"
            }
            !onlyLettersRegex.matches(value) -> {
                message = "Name can only contain letters"
            }
        }
        return message
    }
    fun setPackageType(value: String): String {
        _packageType.value = value
        var message = ""
        when {
            value.isBlank() -> {
                message = "Package type cannot be empty"
            }
        }
        return message
    }
    fun setPackagePrice(value: Double): String {
        _packagePrice.value = value
        var message = ""
        when {
            value <= 0.0 -> {
                message = "Price must be greater than 0"
            }
            value >= 1000000 -> {
                message = "Price must be less than 1,000,000"
            }
        }
        return message
    }
    fun setCustomerLocation(value: String): String {
        _customerLocation.value = value
        var message = ""
        when {
            value.isBlank() -> {
                message = "Location cannot be empty"
            }
        }
        return message
    }
    fun setReceiverLat(value: Double){}
    fun setReceiverLng(value: Double) { _receiverLng.value = value }

    fun onValidation(
        phone: String,
        name: String,
        type: String,
        price: Double,
        location: String,
        lat: Double,
        lng: Double
    ): Boolean {
        return when {
            phone.isBlank() || phone.length <= 5 || phone.length >= 15 -> false
            name.isBlank() || name.contains(
                Regex(".*[0-9].*")
            ) -> false
            type.isBlank() -> false
            price <= 0.0 || price >= 1000000 -> false
            location.isBlank() -> false
            lat == 0.0 || lng == 0.0 -> false
            else -> true
        }
    }

    fun createPackage(request: CreatePackageRequest) {
        _createPackageUIState.value = CreatePackageUIState.Loading
        viewModelScope.launch {
            try {
                Log.d( "CreatePackageViewModel", "Creating package with request: $request" )
                val response = repository.createPackage(request)
                if (response.errors != null) {
                    _createPackageUIState.value = CreatePackageUIState.Error(response.message.orEmpty())
                } else {
                    _createPackageUIState.value = CreatePackageUIState.Success
                }
                Log.d( "CreatePackageViewModel", "Create package response: $response" )
            } catch (e: Exception) {
                _createPackageUIState.value = CreatePackageUIState.Error(e.message ?: "An unexpected error occurred")
                Log.d( "CreatePackageViewModel", "Error creating package", e )
            }
        }
    }
}
