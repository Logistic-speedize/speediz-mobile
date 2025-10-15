package com.example.speediz.core.data.model

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName ("data")
    var data: Data? = Data(),
)
{
    data class Data(
        @SerializedName("token")
        var accessToken : String? = null,
        @SerializedName("data")
        var user: User? = User()
    ){
        data class User(
            @SerializedName("id")
            var userId: Int? = 0,
            @SerializedName("email")
            var email: String? = "",
            @SerializedName("role")
            var role: Int? = 3
        )
    }
}