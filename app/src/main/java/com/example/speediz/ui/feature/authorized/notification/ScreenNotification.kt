package com.example.speediz.ui.feature.authorized.notification

import android.Manifest
import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class ScreenNotification: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //create notification channel
        createNotificationChannel()
        //manage permission for android 13 and above
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    showNotification()
                }
            }
        setContent {
            NotificationButton {
                //check permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    when {
                        ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED -> {
                            // Already granted
                            showNotification()
                        }
                        else -> {
                            // Request permission at runtime
                            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }
                } else {
                    // Android 12 and below
                    showNotification()
                }
            }
        }
    }

    // Create notification channel
    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "channelId"
            val channelName = "MyChannel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    // Show notification
    private fun showNotification(){
        val builder = NotificationCompat.Builder(this, "Channel_Id")
            .setSmallIcon(R.drawable.ic_dialog_info)
            .setContentTitle("Title")
            .setContentText("Content")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        if (
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
        ){
            with(NotificationManagerCompat.from(this)){
                notify(1, builder.build())
            }
        }
    }
}
// Button to trigger notification
@Composable
fun NotificationButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("Show Notification")
    }
}
@Preview (showBackground = true)
@Composable
fun notificationPreview(){
    NotificationButton(
        onClick = { /*TODO*/ }
    )
}