package com.example.speediz.ui.feature.authorized.delivery.express.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speediz.core.data.model.CompletedStatusRequest
import com.example.speediz.core.data.model.ExpressDetailResponse
import com.example.speediz.core.data.model.PickUpStatusRequest
import com.example.speediz.core.data.model.StatusRequest
import com.example.speediz.core.data.model.TrackingLocationRequest
import com.example.speediz.core.repository.ExpressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpressDetailViewModel @Inject constructor(
    private val repository : ExpressRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<ExpressDetailUiState>(ExpressDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _completedStatus = MutableStateFlow<StatusUiState>(StatusUiState.Loading)
    val completedStatus = _completedStatus.asStateFlow()
    private val _cancelStatus = MutableStateFlow<StatusUiState>(StatusUiState.Loading)
    val cancelStatus = _cancelStatus.asStateFlow()
    private  val _rollbackStatus = MutableStateFlow<StatusUiState>(StatusUiState.Loading)
    val rollbackStatus = _rollbackStatus.asStateFlow()

    private val _pickUpStatus = MutableStateFlow<StatusUiState>(StatusUiState.Loading)
    val pickUpStatus = _pickUpStatus.asStateFlow()
    private val _expressDetail = MutableStateFlow<ExpressDetailResponse?>(null)
    val expressDetail = _expressDetail.asStateFlow()

    private val _trackingLocation = MutableStateFlow<TrackingUiState>(TrackingUiState.Loading)
    val trackingLocation = _trackingLocation.asStateFlow()
    fun getExpressDetail(id: Int) {
       viewModelScope.launch {
           _uiState.value = ExpressDetailUiState.Loading

           val response = repository.expressDetail(id)
           try {
                _uiState.value = ExpressDetailUiState.Success(response)
                _expressDetail.value = response
              } catch (e: Exception) {
                _uiState.value = ExpressDetailUiState.Error(e.message ?: "Unknown Error")
           }
       }
    }

    fun getCompletedStatus(request: CompletedStatusRequest) {
        viewModelScope.launch {
            _completedStatus.value = StatusUiState.Loading

            try {
                val response = repository.completedStatusExpress(request)
                _completedStatus.value = StatusUiState.Success(response.message.toString())
            } catch (e: Exception) {
                _completedStatus.value = StatusUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun getCancelStatus(request: StatusRequest) {
        viewModelScope.launch {
            _cancelStatus.value = StatusUiState.Loading

            try {
                val response = repository.cancelStatusExpress(request)
                _cancelStatus.value = StatusUiState.Success(response.message.toString())
            } catch (e: Exception) {
                _cancelStatus.value = StatusUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun getRollbackStatus(request: StatusRequest) {
        viewModelScope.launch {
            _rollbackStatus.value = StatusUiState.Loading

            try {
                val response = repository.rollbackStatusExpress(request)
                _rollbackStatus.value = StatusUiState.Success(response.message.toString())
            } catch (e: Exception) {
                _rollbackStatus.value = StatusUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun getPickUpStatus(request: PickUpStatusRequest) {
        viewModelScope.launch {
            _pickUpStatus.value = StatusUiState.Loading

            try {
                val response = repository.pickUpStatusExpress(request)
                _pickUpStatus.value = StatusUiState.Success(response.message.toString())
            } catch (e: Exception) {
                _pickUpStatus.value = StatusUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun getTrackingDeliveryLocation(request : TrackingLocationRequest) {
        viewModelScope.launch {
            try {
                val response = repository.trackingDeliveryLocation(request)
                _trackingLocation.value = TrackingUiState.Success(response.message.toString())
                Log.d("ExpressViewModel" , "Tracking location response: $response" )
            } catch (e: Exception) {
                Log.d("ExpressViewModel" , "Error tracking location: ${e.message}" )
            }
            delay(5000)
        }
    }
}