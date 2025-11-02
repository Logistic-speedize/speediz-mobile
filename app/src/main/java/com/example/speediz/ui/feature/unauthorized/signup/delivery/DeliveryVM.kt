package com.example.speediz.ui.feature.unauthorized.signup.delivery

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalGraphicsContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.data.model.SignUpDeliveryRequest
import com.example.speediz.core.repository.SignUpRepository
import com.example.speediz.ui.feature.unauthorized.signup.SignUPState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DeliveryVM @Inject constructor(
    private val repository: SignUpRepository
) : ViewModel() {

    private val _signUpUiState = MutableStateFlow<SignUPState>(SignUPState.Idle)
    val signUpUiState: StateFlow<SignUPState> = _signUpUiState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isError = MutableStateFlow<String?>(null)
    val isError: StateFlow<String?> = _isError

    // Form fields
    val firstName = mutableStateOf("")
    val lastName = mutableStateOf("")
    val dob = mutableStateOf("")
    val gender = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val passwordConfirm = mutableStateOf("")
    val contactNumber = mutableStateOf("")
    val driverType = mutableStateOf("")
    val zone = mutableStateOf("")
    val nidUri = mutableStateOf<Uri?>(null) // NID image URI

    fun deliverySignUp() {
        // Basic form validation (optional)
        if (firstName.value.isEmpty() || email.value.isEmpty() || password.value.isEmpty()) {
            _isError.value = "Please fill all required fields"
            return
        }

        val request = SignUpDeliveryRequest(
            firstname = firstName.value,
            lastname = lastName.value,
            dob = dob.value,
            gender = gender.value,
            email = email.value,
            password = password.value,
            passwordConfirm = passwordConfirm.value,
            contactNumber = contactNumber.value,
            driverType = driverType.value,
            zone = zone.value,
            image = nidUri.value
        )
        Log.d( "DeliveryVM", "Starting delivery sign up with request: $request")
        _isLoading.value = true
        _signUpUiState.value = SignUPState.Loading

        viewModelScope.launch {
            try {
                val response = repository.deliverySignUp(request)
                _signUpUiState.value = SignUPState.Success("Delivery sign up successful")
                Log.d("DeliveryVM" , "Delivery sign up successful: $response")
            } catch (e: Exception) {
                _signUpUiState.value = SignUPState.Error(e.message ?: "Sign up failed")
                Log.d("DeliveryVM" , "Delivery sign up failed: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
