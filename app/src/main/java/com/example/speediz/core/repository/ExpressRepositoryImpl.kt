package com.example.speediz.core.repository

import com.example.speediz.core.data.delivery.CompletedStatusRequest
import com.example.speediz.core.data.delivery.ExpressDetailResponse
import com.example.speediz.core.data.delivery.ExpressResponse
import com.example.speediz.core.data.delivery.PickUpStatusRequest
import com.example.speediz.core.data.ResponseErrorModel
import com.example.speediz.core.data.delivery.StatusRequest
import com.example.speediz.core.data.delivery.TrackingLocationRequest
import com.example.speediz.core.network.SafeApiRequest
import com.example.speediz.core.network.services.ApiService
import javax.inject.Inject

class ExpressRepositoryImpl @Inject constructor(
    private val api: ApiService

) : ExpressRepository, SafeApiRequest() {
    override suspend fun express() : ExpressResponse {
        return apiRequest {
            api.deliveryExpress()
        }
    }

    override suspend fun expressDetail(id : Int) : ExpressDetailResponse {
        return apiRequest {
            api.deliveryExpressDetail(id)
        }
    }

    override suspend fun completedStatusExpress(request : CompletedStatusRequest) : ResponseErrorModel {
       return apiRequest {
           api.completedStatus(request)
       }
    }

    override suspend fun cancelStatusExpress(request : StatusRequest) : ResponseErrorModel {
        return  apiRequest {
            api.cancelStatus(request)
        }
    }

    override suspend fun rollbackStatusExpress(request : StatusRequest) : ResponseErrorModel {
        return apiRequest {
            api.rollbackStatus(request)
        }
    }

    override suspend fun pickUpStatusExpress(request: PickUpStatusRequest) : ResponseErrorModel {
        return apiRequest {
            api.pickUpStatus(request)
        }
    }

    override suspend fun trackingDeliveryLocation(request : TrackingLocationRequest) : ResponseErrorModel {
        return apiRequest {
            api.deliveryTracking(request)
        }
    }

}