package com.example.speediz.core.network.util

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.util.Log

open class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("FCM", "Message: ${message.data}")
    }

    override fun onNewToken(token: String) {
        Log.d("FCM", "New token: $token")
    }
}
