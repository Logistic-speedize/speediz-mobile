package com.example.speediz.ui.feature.unauthorized.signup.vendor

import android.util.Log
import androidx.compose.ui.platform.LocalGraphicsContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.data.model.SignInResponse
import com.example.speediz.core.data.model.SignUpVendorRequest
import com.example.speediz.core.repository.SignUpRepository
import com.example.speediz.ui.base.BaseViewModel
import com.example.speediz.ui.feature.unauthorized.signup.SignUPState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class VendorSignUpVM @Inject constructor(
    private val repository: SignUpRepository
) : ViewModel() {
    private var _signUpUiState = MutableStateFlow<SignUPState>(SignUPState.Idle)
    val signUpUiState: StateFlow<SignUPState> = _signUpUiState
    private var _formValidate = MutableStateFlow(false)
    val formValidate = _formValidate

    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading

    private var _isError = MutableStateFlow<String?>(null)
    val isError = _isError

    val firstname = MutableStateFlow("")
    val lastname = MutableStateFlow("")
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")
    val businessName = MutableStateFlow("")
    val address = MutableStateFlow("")
    val dob = MutableStateFlow("")
    val gender = MutableStateFlow("")
    val phoneNumber = MutableStateFlow("")

    fun validateForm(): Boolean {
        val textFields = listOf(
            firstname, lastname, email, password, confirmPassword,
            businessName, address, gender, dob, phoneNumber
        )

        if (textFields.any { it.value.isEmpty() }) {
            _formValidate.value = false
            _isError.value = "All fields are required"
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
            _formValidate.value = false
            _isError.value = "Invalid email format"
            return false
        }

        if (phoneNumber.value.length < 8) {
            _formValidate.value = false
            _isError.value = "Invalid phone number format"
            return false
        }

        if (password.value.length < 6) {
            _formValidate.value = false
            _isError.value = "Password must be at least 6 characters long"
            return false
        }

        if (password.value != confirmPassword.value) {
            _formValidate.value = false
            _isError.value = "Passwords do not match"
            return false
        }

        _formValidate.value = true
        return true
    }



    fun signUpVendor(request: SignUpVendorRequest) {
        _signUpUiState.value = SignUPState.Loading
        viewModelScope.launch {
            val response = repository.vendorSignUp(request)

            if (response.data?.token?.isNotEmpty() == true) {
                _signUpUiState.value = SignUPState.Success("Sign up successful")
                Log.d( "VendorSignUpVM", "Sign up successful. Token: ${response.data.token}" )
            } else {
                _signUpUiState.value = SignUPState.Error("Sign up failed")
            }
        }
    }

}