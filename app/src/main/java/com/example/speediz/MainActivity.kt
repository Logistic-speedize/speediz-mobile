package com.example.speediz

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.speediz.core.application.MySharePreferences
import com.example.speediz.core.network.interceptor.NetworkConnectionInterceptor
import com.example.speediz.ui.feature.unauthorized.signIn.SignInViewModel
import com.example.speediz.ui.graphs.AppNavigation
import com.example.speediz.ui.theme.LightStatusBar
import com.example.speediz.ui.theme.SpeedizTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val sharePreferences: MySharePreferences = hiltViewModel<SignInViewModel>().sharePreferences
            val isLoggedIn = remember { mutableStateOf(sharePreferences.getToken() != null) }
            val role = remember { mutableStateOf(sharePreferences.getUserRole()) }
            val showSplashScreen = remember { mutableStateOf(true) }

            val isInternet = NetworkConnectionInterceptor(applicationContext)
            val isConnected = remember { mutableStateOf(isInternet.isConnected()) }
            val requestPermissionNotification = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                // Handle permission result if needed
            }
            val requestPermissionLocation = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                // Handle permission result if needed
            }

            LaunchedEffect(Unit) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionNotification.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                    requestPermissionLocation.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
            LaunchedEffect( navController, role) {
                navController.addOnDestinationChangedListener {
                    _, destination, _ ->
                    isLoggedIn.value = sharePreferences.getToken() != null
                    role.value = sharePreferences.getUserRole()
                }
            }
            LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(1500)
                showSplashScreen.value = false
            }
            DisposableEffect(Unit) {
                val connectivityReceiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context?, intent: Intent?) {
                        isConnected.value = isInternet.isConnected()
                    }
                }
                val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
                registerReceiver(connectivityReceiver, filter)

                onDispose {
                    unregisterReceiver(connectivityReceiver)
                }
            }

            SpeedizTheme {
               LightStatusBar()
               if (showSplashScreen.value) {
                   ScreenSplashScreen()
               } else {
                  if(!isConnected.value){
                      ScreenNoInternet()
                  } else {
                      AppNavigation(
                          navController = navController ,
                          role = role.value?.toInt() ,
                      )
                  }
               }
           }

        }
    }
}