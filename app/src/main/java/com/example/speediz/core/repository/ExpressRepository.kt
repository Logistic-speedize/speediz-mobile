package com.example.speediz.core.repository

import com.example.speediz.core.data.model.CompletedStatusRequest
import com.example.speediz.core.data.model.ExpressDetailResponse
import com.example.speediz.core.data.model.ExpressResponse
import com.example.speediz.core.data.model.PickUpStatusRequest
import com.example.speediz.core.data.model.ResponseErrorModel
import com.example.speediz.core.data.model.StatusRequest

interface ExpressRepository {
    suspend fun express(): ExpressResponse
    suspend fun expressDetail(id: Int): ExpressDetailResponse

    suspend fun completedStatusExpress(request : CompletedStatusRequest): ResponseErrorModel

    suspend fun cancelStatusExpress( request: StatusRequest ): ResponseErrorModel

    suspend fun rollbackStatusExpress( request: StatusRequest ): ResponseErrorModel

    suspend fun pickUpStatusExpress( request : PickUpStatusRequest): ResponseErrorModel
}