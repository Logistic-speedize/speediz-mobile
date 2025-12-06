// kotlin
package com.example.speediz.ui.feature.authorized.vendor.packageManagement.create

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

//    private val _receiverPhone = MutableStateFlow("")
//    val receiverPhone: StateFlow<String> = _receiverPhone.asStateFlow()
//
//    private val _receiverName = MutableStateFlow("")
//    val receiverName: StateFlow<String> = _receiverName.asStateFlow()

//    private val _packageType = MutableStateFlow("")
//    val packageType: StateFlow<String> = _packageType.asStateFlow()
//
//    private val _packagePrice = MutableStateFlow(0.0)
//    val packagePrice: StateFlow<Double> = _packagePrice.asStateFlow()
//
//    private val _customerLocation = MutableStateFlow("")
//    val customerLocation: StateFlow<String> = _customerLocation.asStateFlow()
//
//    private val _receiverLat = MutableStateFlow(0.0)
//    val receiverLat: StateFlow<Double> = _receiverLat.asStateFlow()
//
//    private val _receiverLng = MutableStateFlow(0.0)
//    val receiverLng: StateFlow<Double> = _receiverLng.asStateFlow()

    // Update methods called by UI to set values before validation
//    fun setReceiverPhone(value: String) { _receiverPhone.value = value }
//    fun setReceiverName(value: String) { _receiverName.value = value }
//    fun setPackageType(value: String) { _packageType.value = value }
//    fun setPackagePrice(value: Double) { _packagePrice.value = value }
//    fun setCustomerLocation(value: String) { _customerLocation.value = value }
//    fun setReceiverLat(value: Double) { _receiverLat.value = value }
//    fun setReceiverLng(value: Double) { _receiverLng.value = value }

//    fun onValidate(): Boolean {
//        return when {
//            receiverPhone.value.isBlank() -> {
//                _createPackageUIState.value = CreatePackageUIState.Error("Receiver phone cannot be empty")
//                false
//            }
//            receiverName.value.isBlank() -> {
//                _createPackageUIState.value = CreatePackageUIState.Error("Receiver name cannot be empty")
//                false
//            }
//            packageType.value.isBlank() -> {
//                _createPackageUIState.value = CreatePackageUIState.Error("Package type cannot be empty")
//                false
//            }
//            packagePrice.value <= 0.0 -> {
//                _createPackageUIState.value = CreatePackageUIState.Error("Package price must be greater than zero")
//                false
//            }
//            customerLocation.value.isBlank() -> {
//                _createPackageUIState.value = CreatePackageUIState.Error("Customer location cannot be empty")
//                false
//            }
//            receiverLat.value == 0.0 || receiverLng.value == 0.0 -> {
//                _createPackageUIState.value = CreatePackageUIState.Error("Receiver coordinates must be set")
//                false
//            }
//            else -> true
//        }
//    }

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
