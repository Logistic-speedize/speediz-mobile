package com.example.speediz.core.repository

import com.example.speediz.core.data.SignInRequest
import com.example.speediz.core.data.SignInResponse
import com.example.speediz.core.network.SafeApiRequest
import com.example.speediz.core.network.services.ApiService
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val api: ApiService
) : SafeApiRequest(), SignInRepository {
    override suspend fun userSignIn(signInRequest : SignInRequest) : SignInResponse {
        return apiRequest {
            api.signIn(signInRequest)
        }
    }
}