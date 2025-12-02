package com.example.speediz.core.data.vendor

import com.google.gson.annotations.SerializedName

data class SignUpVendorRequest(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("dob")
    val dob: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("password_confirmation")
    val passwordConfirm: String,
    @SerializedName("business_name")
    val businessName: String,
    @SerializedName("contact_number")
    val contactNumber: String,
    @SerializedName("address")
    val address: String,
)