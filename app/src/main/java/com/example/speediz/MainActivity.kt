package com.example.speediz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.speediz.core.application.MySharePreferences
import com.example.speediz.ui.feature.unauthorized.signIn.SignInViewModel
import com.example.speediz.ui.graphs.AppNavigation
import com.example.speediz.ui.theme.LightStatusBar
import com.example.speediz.ui.theme.SpeedizTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val sharePreferences: MySharePreferences = hiltViewModel<SignInViewModel>().sharePreferences
            val isLoggedIn = remember { mutableStateOf(sharePreferences.getToken() != null) }
            val role = remember { mutableStateOf(sharePreferences.getUserRole()) }
            val showSplashScreen = remember { mutableStateOf(true) }

            val requestPermissionNotification = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                // Handle permission result if needed
            }

            LaunchedEffect(Unit) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionNotification.launch(android.Manifest.permission.POST_NOTIFICATIONS)
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
                // A surface container using the 'background' color from the theme
           SpeedizTheme {
               LightStatusBar()
               if (showSplashScreen.value) {
                   ScreenSplashScreen()

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