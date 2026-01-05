// kotlin
package com.example.speediz.ui.feature.authorized.vendor.packageManagement.create

import android.text.style.TtsSpan
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var receiverPhone by mutableStateOf("")

    var receiverName by mutableStateOf("")

    var packageType by mutableStateOf("")

    var packagePrice by mutableDoubleStateOf(0.0)

    var customerLocation by mutableStateOf("")

    private val _receiverLat = MutableStateFlow(0.0)
    var receiverLat = _receiverLat.value


    private val _receiverLng = MutableStateFlow(0.0)
    var receiverLngValue = _receiverLng.value

    // Update methods called by UI to set values before validation
    fun setReceiverPhone(value: String): String {
        receiverPhone = value
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
    fun onReceiverNameChange(value: String): String {
        receiverName = value
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
    fun onPackageTypeChange(value: String): String {
        packageType = value
        var message = ""
        when {
            value.isBlank() -> {
                message = "Package type cannot be empty"
            }
        }
        return message
    }
    fun onPackagePriceChange(value: Double): String {
        packagePrice = value
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
    fun onCustomerLocationChange(value: String): String {
        customerLocation = value
        var message = ""
        when {
            value.isBlank() -> {
                message = "Location cannot be empty"
            }
        }
        return message
    }

    fun onPhoneChanged(value: String): String {
        this.receiverPhone = value
        var message = ""
        if (receiverPhone.isEmpty()) {
            message =  "Phone number is required"
        }
        else if (receiverPhone.length !in 6..14) {
            message = "Phone number must be between 6 and 14 digits"
        }
        return message
    }


    fun onValidate(): Boolean{
        val phoneError = setReceiverPhone(receiverPhone)
        val nameError = onReceiverNameChange(receiverName)
        val typeError = onPackageTypeChange(packageType)
        val priceError = onPackagePriceChange(packagePrice)
        val locationError = onCustomerLocationChange(customerLocation)

        return phoneError.isEmpty() &&
                nameError.isEmpty() &&
                typeError.isEmpty() &&
                priceError.isEmpty() &&
                locationError.isEmpty()
    }

    fun createPackage(request: CreatePackageRequest) {
        _createPackageUIState.value = CreatePackageUIState.Loading

        viewModelScope.launch {
            try {
                Log.d("CreatePackageVM", "Request: $request")

                val response = repository.createPackage(request)

                if (response.errors != null) {
                    _createPackageUIState.value =
                        CreatePackageUIState.Error(response.message ?: "Create package failed")
                } else {
                    _createPackageUIState.value = CreatePackageUIState.Success
                }

                Log.d("CreatePackageVM", "Response: $response")

            } catch (e: Exception) {
                _createPackageUIState.value =
                    CreatePackageUIState.Error(e.message ?: "Unexpected error")
            }
        }
    }
}
