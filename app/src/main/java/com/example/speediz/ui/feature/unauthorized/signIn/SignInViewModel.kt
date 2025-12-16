package com.example.speediz.ui.feature.unauthorized.signIn

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.application.MySharePreferences
import com.example.speediz.core.data.SignInRequest
import com.example.speediz.core.repository.SignInRepository
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: SignInRepository,
    val sharePreferences : MySharePreferences
    ) : ViewModel() {
        private val _signInState = MutableStateFlow<SignInState>(SignInState.Idle)
    private val _isLoggedIn = MutableStateFlow( sharePreferences.getToken() != null )
    val isLoggedIn : StateFlow<Boolean> = _isLoggedIn
    val signInState : StateFlow<SignInState> = _signInState
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    fun onEmailChanged(email: String): String {
        this.email = email
        var message = ""
        if (email.isEmpty()) message = "Email is required"
        else if (! Patterns.EMAIL_ADDRESS.matcher(email).matches()) message = "Invalid email format"
        return message
    }
    private var _role = MutableStateFlow(3)
    val role = _role
    fun onPasswordChanged(newPassword: String): String {
        password = newPassword
        var message = ""
        if (password.isEmpty()) message = "Password is required"
        else if (password.length < 6) message = "Password must be at least 6 characters long"
        return message
    }
    private fun validateInput(){
        val emailError = if(email.isEmpty()) "Email is required"
        else if (! Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Invalid email format"
        else null

        val passwordError = if(password.isEmpty()) "Password is required"
        else if (password.length < 6) "Password must be at least 6 characters long"
        else null
        if(emailError != null || passwordError != null) {
            _signInState.value = SignInState.ValidationError(emailError, passwordError)
            Log.e("TAG", "valideteInput: ${_signInState.value}")
        }else{
            _signInState.value = SignInState.Idle
        }
    }

    private suspend fun fetchFcmToken(): String? {
        return try {
            FirebaseMessaging.getInstance().token.await()
        } catch (e: Exception) {
            Log.d("SignInVM", "Failed to fetch FCM token", e)
            null
        }
    }
    fun signIn( signInRequest : SignInRequest ) {
        validateInput()
        if ( _signInState.value is SignInState.ValidationError) return
        viewModelScope.launch {
            _signInState.value = SignInState.Loading
            try {
                val fcmToken = fetchFcmToken()
                val request = signInRequest.copy(
                    email = email,
                    password = password,
                    deviceToken = fcmToken ?: ""
               )
                Log.d( "TAG", "signIn: Login ${request}" )
                val response = repository.userSignIn(request)

                val success = response.data?.accessToken?:""
                Log.d("TAG", "Sign in successful. Token: ${response.data?.accessToken}")
                if (success.isNotEmpty()) {
                    _signInState.value = SignInState.Success(response.data?.accessToken.toString())
                    _isLoggedIn.value = true
                    sharePreferences.saveToken(response.data?.accessToken.toString(), response.data?.user?.role.toString())
                    _role.value = response.data?.user?.role ?: 3
                    Log.d( "TAG", "signIn: role ${response.data?.user?.role}" )
                } else {
                    _signInState.value = SignInState.Error("Invalid response from server")
                }
            } catch (e: Exception) {
                Log.e("TAG", "Sign in failed: ${e.message}")
                _signInState.value = SignInState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            sharePreferences.clearToken()
            Log.d("TAG", "User signed out.")
        }
    }
    fun resetState() {
        _signInState.value = SignInState.Idle
    }

}