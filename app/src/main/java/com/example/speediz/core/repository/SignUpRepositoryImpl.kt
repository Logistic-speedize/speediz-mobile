package com.example.speediz.core.repository

import android.content.Context
import android.net.Uri
import com.example.speediz.core.data.delivery.SignUpDeliveryRequest
import com.example.speediz.core.data.delivery.SignUpDriverResponse
import com.example.speediz.core.data.vendor.SignUpVendorRequest
import com.example.speediz.core.data.vendor.SignUpVendorResponse
import com.example.speediz.core.network.SafeApiRequest
import com.example.speediz.core.network.services.ApiService
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val api: ApiService,
    @ApplicationContext private val context: Context
) : SignUpRepository, SafeApiRequest() {

    override suspend fun vendorSignUp(signUpVendorRequest: SignUpVendorRequest): SignUpVendorResponse {
        return apiRequest {
            api.vendorSignUp(signUpVendorRequest)
        }
    }

    override suspend fun deliverySignUp(signUpDeliveryRequest: SignUpDeliveryRequest): SignUpDriverResponse {
        return apiRequest {
            api.deliverySignUp(
                signUpDeliveryRequest.firstname.toRequestBody(MultipartBody.FORM),
                signUpDeliveryRequest.lastname.toRequestBody(MultipartBody.FORM),
                signUpDeliveryRequest.dob.toRequestBody(MultipartBody.FORM),
                signUpDeliveryRequest.gender.toRequestBody(MultipartBody.FORM),
                signUpDeliveryRequest.email.toRequestBody(MultipartBody.FORM),
                signUpDeliveryRequest.password.toRequestBody(MultipartBody.FORM),
                signUpDeliveryRequest.passwordConfirm.toRequestBody(MultipartBody.FORM),
                signUpDeliveryRequest.contactNumber.toRequestBody(MultipartBody.FORM),
                signUpDeliveryRequest.driverType.toRequestBody(MultipartBody.FORM),
                signUpDeliveryRequest.zone.toRequestBody(MultipartBody.FORM),
                createMultipartFromUri("image" , signUpDeliveryRequest.image),
                createMultipartFromUri("nid" , signUpDeliveryRequest.nid),
                signUpDeliveryRequest.address.toRequestBody(MultipartBody.FORM),
            )
        }
    }
    private fun createMultipartFromUri(partName: String, uri: Uri?): MultipartBody.Part? {
        if (uri == null) return null
        return try {
            val contentResolver = context.contentResolver
            val mimeType = contentResolver.getType(uri) ?: "image/*"
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            val bytes = inputStream.readBytes()
            inputStream.close()
            val requestBody = bytes.toRequestBody(mimeType.toMediaTypeOrNull())
            val fileName = getFileName(uri)
            MultipartBody.Part.createFormData(partName, fileName, requestBody)
        } catch (e: Exception) {
            null
        }
    }


    private fun getFileName(uri: Uri): String {
        var name = "nid_image.jpeg"
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndexOrThrow(android.provider.MediaStore.MediaColumns.DISPLAY_NAME)
            if (cursor.moveToFirst() && nameIndex >= 0) {
                name = cursor.getString(nameIndex)
            }
        }
        return name
    }

}
