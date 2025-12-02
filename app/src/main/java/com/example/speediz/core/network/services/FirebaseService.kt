package com.example.speediz.core.network.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "MyFirebaseMessagingService"
    }
    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(TAG, "Message: ${message.data}")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")
        sendRegistrationToServer(token)
      //  getTokenManually()
    }

    private fun sendRegistrationToServer(token: String) {
        Log.d(TAG, "Token sent to server: $token")
    }

    // Optional: You can manually get the token anytime:
//    public fun getTokenManually() {
//        FirebaseMessaging.getInstance().token
//            .addOnCompleteListener { task ->
//                if (!task.isSuccessful) {
//                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
//                    return@addOnCompleteListener
//                }
//                val token = task.result
//                Log.d(TAG, "Current token: $token")
//                sendRegistrationToServer(token)
//            }
//    }
}