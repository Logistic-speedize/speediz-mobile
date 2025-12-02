package com.example.speediz.core.repository

import com.example.speediz.core.data.SignInRequest
import com.example.speediz.core.data.SignInResponse

interface SignInRepository{
    suspend fun userSignIn( signInRequest : SignInRequest) : SignInResponse
}