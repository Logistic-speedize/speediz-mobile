package com.example.speediz.core.repository

import com.example.speediz.core.data.delivery.SignUpDeliveryRequest
import com.example.speediz.core.data.delivery.SignUpDriverResponse
import com.example.speediz.core.data.vendor.SignUpVendorRequest
import com.example.speediz.core.data.vendor.SignUpVendorResponse

interface SignUpRepository {
    suspend fun vendorSignUp( signUpVendorRequest : SignUpVendorRequest) : SignUpVendorResponse

    suspend fun deliverySignUp( signUpDeliveryRequest : SignUpDeliveryRequest) : SignUpDriverResponse
}