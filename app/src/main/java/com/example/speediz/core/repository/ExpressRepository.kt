package com.example.speediz.core.repository

import com.example.speediz.core.data.delivery.CompletedStatusRequest
import com.example.speediz.core.data.delivery.ExpressDetailResponse
import com.example.speediz.core.data.delivery.ExpressResponse
import com.example.speediz.core.data.delivery.PickUpStatusRequest
import com.example.speediz.core.data.ResponseErrorModel
import com.example.speediz.core.data.delivery.StatusRequest
import com.example.speediz.core.data.delivery.TrackingLocationRequest

interface ExpressRepository {
    suspend fun express(): ExpressResponse
    suspend fun expressDetail(id: Int): ExpressDetailResponse

    suspend fun completedStatusExpress(request : CompletedStatusRequest): ResponseErrorModel

    suspend fun cancelStatusExpress( request: StatusRequest ): ResponseErrorModel

    suspend fun rollbackStatusExpress( request: StatusRequest ): ResponseErrorModel

    suspend fun pickUpStatusExpress( request : PickUpStatusRequest): ResponseErrorModel

    suspend fun trackingDeliveryLocation(request: TrackingLocationRequest): ResponseErrorModel
}