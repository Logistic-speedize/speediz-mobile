package com.example.speediz.core.repository

import com.example.speediz.core.data.model.ExpressDetailResponse
import com.example.speediz.core.data.model.ExpressResponse

interface ExpressRepository {
    suspend fun express(): ExpressResponse
    suspend fun expressDetail(id: Int): ExpressDetailResponse
}