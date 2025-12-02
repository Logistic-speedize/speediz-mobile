package com.example.speediz.core.data.vendor

import com.google.gson.annotations.SerializedName

data class SignUpVendorResponse(
    @SerializedName("data")
    val data: VendorData?,
    @SerializedName("meta")
    val meta: Meta?
)

data class VendorData(
    @SerializedName("user")
    val user: User?,
    @SerializedName("token")
    val token: String?
)

data class User(
    @SerializedName("id")
    val id: Int,
    @SerializedName("role")
    val role: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("account_status")
    val accountStatus: Int,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)

data class Meta(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: Message?
)

data class Message(
    @SerializedName("title")
    val title: String,
    @SerializedName("message")
    val message: String
)
