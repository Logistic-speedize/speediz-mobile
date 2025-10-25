package com.example.speediz.ui.feature.unauthorized.signIn

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.application.MySharePreferences
import com.example.speediz.core.data.model.SignInRequest
import com.example.speediz.core.repository.SignInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
    fun onEmailChanged(newEmail: String) {
        email = newEmail
       validateInput()
    }
    private var _role = MutableStateFlow(3)
    val role = _role
    fun onPasswordChanged(newPassword: String) {
        password = newPassword
      validateInput()
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
    fun signIn( signInRequest : SignInRequest ) {
        validateInput()
        if ( _signInState.value is SignInState.ValidationError) return
        viewModelScope.launch {
            _signInState.value = SignInState.Loading
            try {
                val response = repository.userSignIn(signInRequest)
                val success = response.data?.accessToken?:""
                Log.d("TAG", "Sign in successful. Token: ${response.data?.accessToken}")
                if (success.isNotEmpty()) {
                    _signInState.value = SignInState.Success(response.data?.accessToken.toString())
                    _isLoggedIn.value = true
                    sharePreferences.saveToken(response.data?.accessToken.toString())
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
    fun resetState() {
        _signInState.value = SignInState.Idle
    }
}