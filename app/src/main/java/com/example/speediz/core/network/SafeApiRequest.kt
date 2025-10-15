package com.example.speediz.core.network

import com.example.speediz.core.data.model.ResponseErrorModel
import com.example.speediz.ui.utils.MyException
import com.google.gson.GsonBuilder
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {
    suspend fun < T : Any> apiRequest(
        call : suspend () -> Response<T>
    ): T{
        val response = call.invoke()
        if(response.isSuccessful){
            return response.body() ?: throw Exception("Response body is null")
        }else{
            val error = response.errorBody()?.string()
            val message = StringBuilder()
            error?.let{
                try{
                    val errorResponse = JSONObject(it).toString()
                    val gsonBuilder = GsonBuilder()
                    val gson = gsonBuilder.create()
                    val errorData = gson.fromJson(errorResponse, ResponseErrorModel::class.java)
                    message.append(errorData.message ?: "Unknown error")

                }catch ( e: Exception) {
                    message.append("Error parsing error response: ${e.message ?: "Unknown error"}")
                }
            }
            throw MyException(message.toString())
        }
    }

}