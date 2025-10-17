package com.example.speediz.core.repository

import com.example.speediz.core.data.model.SignInRequest
import com.example.speediz.core.data.model.SignInResponse
import com.example.speediz.core.network.SafeApiRequest
import com.example.speediz.core.network.services.ApiService
import javax.inject.Inject

interface SignInRepository{
    suspend fun userSignIn( signInRequest : SignInRequest) : SignInResponse
}