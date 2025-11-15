package com.example.speediz.ui.feature.authorized.delivery.notification

import androidx.compose.runtime.Composable

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.messaging.FirebaseMessaging

@Composable
fun ScreenNotification() {
    var fcmToken by remember { mutableStateOf("Fetching...") }

    // Launcher to request notification permission (Android 13+)
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d("FCM", "Notification permission granted")
        } else {
            Log.d("FCM", "Notification permission denied")
        }
    }

    // Request permission and fetch token when screen loads
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            fcmToken = if (task.isSuccessful) {
                task.result
            } else {
                "Failed to get token"
            }
            Log.d("FCM", "Device token: $fcmToken")
        }
    }

    // UI
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "FCM Token:")
            Text(
                text = fcmToken,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Button(onClick = {
                // Refresh token manually
                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    fcmToken = if (task.isSuccessful) task.result else "Failed to get token"
                }
            }) {
                Text("Refresh Token")
            }
        }
    }
}
