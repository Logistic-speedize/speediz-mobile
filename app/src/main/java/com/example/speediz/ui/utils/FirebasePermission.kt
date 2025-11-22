package com.example.speediz.ui.utils

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging

fun hasFirebasePermission(): Boolean {
    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.w("MyFirebaseMessagingService", "Fetching FCM token failed", task.exception)
            return@addOnCompleteListener
        }
        val token = task.result
        Log.d("MyFirebaseMessagingService", "FCM Token (manual): $token")
    }

    FirebaseMessaging.getInstance().subscribeToTopic("speediz")
        .addOnCompleteListener { task ->
            var msg = "Subscribed to speediz topic"
            if (!task.isSuccessful) {
                msg = "Failed to subscribe to speediz topic"
            }
            Log.d("MyFirebaseMessagingService", msg)
        }
    return true
}
