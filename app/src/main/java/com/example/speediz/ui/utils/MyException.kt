package com.example.speediz.ui.utils

import com.mapbox.base.common.logger.model.Message

class MyException(message: String) : Exception(message)
class NoInternetException(message: String) : Exception(message)
class MapBoxException(message: String) : Exception(message)
class LocationPermissionException(message: String) : Exception(message)