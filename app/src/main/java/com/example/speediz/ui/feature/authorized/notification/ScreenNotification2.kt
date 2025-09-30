package com.example.speediz.ui.feature.authorized.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.R
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat


//create notification channel
//show notification
//create composable
//manage permission for android 13 and above
//check permission in set content
//use notification manager to create channel
//use notification compat to build notification
//use notification manager compat to show notification
//set small icon, title, text, priority for notification
//check permission before showing notification
class ScreenNotification2: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ){
                        isGranted -> if (isGranted){
                    showNotification()
                }
            }
        createNotificationChannel()
        setContent {
            NotificationButton2 {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    when {
                        ContextCompat.checkSelfPermission(
                            this, Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED -> {
                            showNotification()
                        }
                        else -> {
                            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }
                }else {
                    showNotification()
                }
            }
        }
    }
    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "channelId"
            val channelName = "MyChannel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            val notificationManger = getSystemService(NotificationManager::class.java)
            notificationManger.createNotificationChannel(channel)
        }
    }
    private fun showNotification() {
        val builder = NotificationCompat.Builder(this, "channelId")
            .setSmallIcon(R.drawable.ic_dialog_info)
            .setContentTitle("Title")
            .setContentText("Text")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        if (
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ){
            with (NotificationManagerCompat.from(this)){
                notify(1, builder.build())
            }
        }
    }

}
//class ScreenNotification2: ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        createNotificationChannel()
//        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
//            isGranted ->
//            if (isGranted){
//                showNotification()
//            }
//        }
//        setContent {
//            NotificationButton2 {
//                if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
//                    when {
//                        ContextCompat.checkSelfPermission(
//                            this, android.Manifest.permission.POST_NOTIFICATIONS
//                        ) == PackageManager.PERMISSION_GRANTED -> {
//                            // Already granted
//                            showNotification()
//                        }
//                        else -> {
//                            // Request permission at runtime
//                            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
//                        }
//                    }
//                } else {
//                    // Android 12 and below
//                    showNotification()
//                }
//            }
//
//        }
//    }
//    private fun createNotificationChannel(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channelId = "channelId"
//            val channelName = "MyChannel"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(channelId, channelName, importance)
//            val notificationManager = getSystemService(NotificationManager::class.java)
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
//    private fun showNotification(){
//        val builder = NotificationCompat.Builder(this, "channelId")
//            .setSmallIcon(android.R.drawable.ic_dialog_info)
//            .setContentTitle("Sample Notification")
//            .setContentText("This is a test notification.")
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//        if (
//            ContextCompat.checkSelfPermission(
//                this, android.Manifest.permission.POST_NOTIFICATIONS
//            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
//        ){
//            with(NotificationManagerCompat.from(this)){
//                notify(1, builder.build())
//            }
//        }
//    }
//}
@Composable
fun NotificationButton2(
    onClick : () -> Unit
){
    Button(
        onClick = onClick
    ) {
        Text(text = "Show Notification")
    }

}