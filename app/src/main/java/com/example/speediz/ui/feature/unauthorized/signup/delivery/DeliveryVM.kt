package com.example.speediz.ui.feature.unauthorized.signup.delivery

import android.net.Uri
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.data.delivery.SignUpDeliveryRequest
import com.example.speediz.core.repository.SignUpRepository
import com.example.speediz.ui.feature.unauthorized.signup.SignUPState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class DeliveryVM @Inject constructor(
    private val repository: SignUpRepository
) : ViewModel() {

    private val _signUpUiState = MutableStateFlow<SignUPState>(SignUPState.Idle)
    val signUpUiState: StateFlow<SignUPState> = _signUpUiState

    // Form fields
    var firstname by mutableStateOf("")
    var lastname by mutableStateOf("")
    var dob by mutableStateOf("")
    var gender by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordConfirm by mutableStateOf("")
    var contactNumber by mutableStateOf("")
    var driverType by mutableStateOf("")
    var zone by mutableStateOf("")
    var address by mutableStateOf("")
    var nidUri by mutableStateOf<Uri?>(null) // NID image URI
    var imageUri by mutableStateOf<Uri?>(null) // Profile image URI

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
    fun onContactNumberChanged(newContactNumber: String): String {
        this. contactNumber = newContactNumber
        var message = ""
        if ( contactNumber.isEmpty()) message = "Contact number is required"
        else if (!Patterns.PHONE.matcher( contactNumber).matches()) message = "Invalid contact number format"
        return message
    }
    fun onZoneChanged(newLocation: String): String {
        this.zone = newLocation
        var message = ""
        if (zone.isEmpty()) message = "Address is required"
        return message
    }

    fun onAddressChanged(newAddress: String): String {
        this.address = newAddress
        var message = ""
        if (address.isEmpty()) message = "Address is required"
        return message
    }

    fun onDriverTypeChanged(newDriverType: String): String {
        this.driverType = newDriverType
        var message = ""
        if (driverType.isEmpty()) message = "Driver type is required"
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
        passwordConfirm = newConfirmPassword
        var message = ""
        if (passwordConfirm.isEmpty()) message = "Confirm password is required"
        else if (passwordConfirm != password) message = "Passwords do not match"
        return message
    }
    fun onImageChanged(newImageUri: Uri?): String {
        this.imageUri = newImageUri
        var message = ""
        if (imageUri == null) {
           message = "Image is required"
        }
        return message
    }

    fun onNidImageChanged(newNidUri: Uri?): String {
        this.nidUri = newNidUri
        var message = ""
        if (nidUri == null) {
            message = "NID image is required"
            Log.d("DeliveryVM", "NID image URI is null")
        }
        return message
    }
    fun onValidate(): Boolean {
        val firstNameError = onFirstnameChanged(firstname)
        val lastNameError = onLastNameChanged(lastname)
        val dobError = onDOBChanged(dob)
        val genderError = onGenderChanged(gender )
        val contactNumberError = onContactNumberChanged(contactNumber)
        val driverTypeError = onDriverTypeChanged(driverType)
        val zoneError = onZoneChanged(zone)
        val emailError = onEmailChanged(email)
        val passwordError = onPasswordChanged(password)
        val confirmPasswordError = onConfirmPasswordChanged(passwordConfirm)
        val nidImageError = onNidImageChanged(nidUri)
        val imageProfileError = onImageChanged(imageUri)
        val addressError = onAddressChanged(address)
        return listOf(
            firstNameError,
            lastNameError,
            dobError,
            genderError,
            contactNumberError,
            driverTypeError,
            zoneError,
            emailError,
            passwordError,
            confirmPasswordError,
            nidImageError,
            imageProfileError,
            addressError
        ).all { it.isEmpty() }
    }



    fun deliverySignUp() {

        val request = SignUpDeliveryRequest(
            firstname = this.firstname,
            lastname = this.lastname,
            dob = this.dob,
            gender = this.gender.lowercase(),
            email = this.email,
            password = this.password,
            passwordConfirm = this.passwordConfirm,
            contactNumber = this.contactNumber,
            driverType = this.driverType,
            zone = this.zone,
            image = this.imageUri,
            address = this.address,
            nid = this.nidUri
        )
        viewModelScope.launch {
            try {
                val response = repository.deliverySignUp(request)
                _signUpUiState.value = SignUPState.Success("Delivery sign up successful")
                Log.d("DeliveryVM" , "Delivery sign up successful: $response")
            } catch (e: Exception) {
                _signUpUiState.value = SignUPState.Error(e.message ?: "Sign up failed")
                Log.d("DeliveryVM" , "Delivery sign up failed: ${e.message}")
            }
        }
    }
}
