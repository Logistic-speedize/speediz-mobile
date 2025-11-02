package com.example.speediz.core.repository

import com.example.speediz.core.data.model.SignInRequest
import com.example.speediz.core.data.model.SignInResponse
import com.example.speediz.core.data.model.SignUpDeliveryRequest
import com.example.speediz.core.data.model.SignUpDriverResponse
import com.example.speediz.core.data.model.SignUpVendorRequest
import com.example.speediz.core.data.model.SignUpVendorResponse

interface SignUpRepository {
    suspend fun vendorSignUp( signUpVendorRequest : SignUpVendorRequest) : SignUpVendorResponse

    suspend fun deliverySignUp( signUpDeliveryRequest : SignUpDeliveryRequest) : SignUpDriverResponse
}