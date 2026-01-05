package com.example.speediz.ui.feature.unauthorized.signup.vendor

import android.os.Build
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.data.vendor.SignUpVendorRequest
import com.example.speediz.core.repository.SignUpRepository
import com.example.speediz.ui.feature.unauthorized.signup.SignUPState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeParseException
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class VendorSignUpVM @Inject constructor(
    private val repository: SignUpRepository
) : ViewModel() {
    private var _signUpUiState = MutableStateFlow<SignUPState>(SignUPState.Idle)
    val signUpUiState: StateFlow<SignUPState> = _signUpUiState

    var firstname by mutableStateOf("")
    var lastname by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var businessName by mutableStateOf("")
    var address by mutableStateOf("")
    var dob by mutableStateOf("")
    var gender by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var email by mutableStateOf("")

    fun onFirstnameChanged(newFirstName: String): String {
       this.firstname = newFirstName
        var message = ""
        if (firstname.isEmpty()) message = "First name is required"
        else if (firstname.length < 2) message = "First name must be at least 2 characters long"
        else if (firstname.length > 50) message = "First name must be less than 50 characters long"
        else if (firstname.any { it.isDigit() }) message = "First name cannot contain numbers"
        return message
    }

    fun onLastNameChanged(newLastName: String): String {
        this.lastname = newLastName
        var message = ""
        if (lastname.isEmpty()) message = "Last name is required"
        else if (lastname.length < 2) message = "Last name must be at least 2 characters long"
        else if (lastname.length > 50) message = "Last name must be less than 50 characters long"
        else if (lastname.any { it.isDigit() }) message = "Last name cannot contain numbers"
        return message
    }

    fun onDOBChanged(newDOB: String): String {
        this.dob = newDOB
        var message = ""
        if (dob.isEmpty()) {
            message = "Date of Birth is required"
            return message
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dobDate = sdf.parse(dob) ?: return  "Invalid Date of Birth"
        val today = Calendar.getInstance()
        val birthDay = Calendar.getInstance()

        birthDay.time = dobDate
        val age = today.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR)


        if (age < 18)  message = "You must be at least 18 years old"

        return message
    }

    fun onGenderChanged(newGender: String): String {
        this.gender = newGender
        var message = ""
        if (gender.isEmpty()) message = "Gender is required"
        return message
    }

    fun onBusinessNameChanged(newBusinessName: String): String {
        this.businessName = newBusinessName
        var message = ""
        if (businessName.isEmpty()) message = "Business name is required"
        return message
    }

    fun onContactNumberChanged(newContactNumber: String): String {
        this.phoneNumber = newContactNumber
        var message = ""
        if (phoneNumber.isEmpty()) message = "Contact number is required"
        else if (!Patterns.PHONE.matcher(phoneNumber).matches()) message = "Invalid contact number format"
        return message
    }

    fun onLocationChanged(newLocation: String): String {
        this.address = newLocation
        var message = ""
        if (address.isEmpty()) message = "Address is required"
        return message
    }

    fun onEmailChanged(newEmail: String): String {
        this.email = newEmail
        var message = ""
        if (email.isEmpty()) message = "Email is required"
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) message = "Invalid email format"
        return message
    }

    fun onPasswordChanged(newPassword: String): String {
        password = newPassword
        var message = ""
        if (password.isEmpty()) message = "Password is required"
        else if (password.length < 6) message = "Password must be at least 6 characters long"
        return message
    }
    fun onConfirmPasswordChanged(newConfirmPassword: String): String {
        confirmPassword = newConfirmPassword
        var message = ""
        if (confirmPassword.isEmpty()) message = "Confirm password is required"
        else if (confirmPassword != password) message = "Passwords do not match"
        return message
    }

    fun onValidateInput(): Boolean {
        val firstNameError = onFirstnameChanged(firstname)
        val lastNameError = onLastNameChanged(lastname)
        val dobError = onDOBChanged(dob)
        val genderError = onGenderChanged(gender )
        val businessNameError = onBusinessNameChanged(businessName)
        val contactNumberError = onContactNumberChanged(phoneNumber)
        val locationError = onLocationChanged(address)
        val emailError = onEmailChanged(email)
        val passwordError = onPasswordChanged(password)
        val confirmPasswordError = onConfirmPasswordChanged(confirmPassword)
        return listOf(
            firstNameError,
            lastNameError,
            dobError,
            genderError,
            businessNameError,
            contactNumberError,
            locationError,
            emailError,
            passwordError,
            confirmPasswordError
        ).all { it.isEmpty() }
    }

    fun signUpVendor(request: SignUpVendorRequest) {
        viewModelScope.launch {
            Log.d( "VendorSignUpVM", "Starting sign up for: $request" )
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