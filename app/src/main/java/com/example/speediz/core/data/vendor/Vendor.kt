package com.example.speediz.core.data.vendor

import com.example.speediz.R

data class Vendor(
    val id: String,
    val email: String = "",
    val profile: Int = R.drawable.img_default_profile
)