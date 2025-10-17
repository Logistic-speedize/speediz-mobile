package com.example.speediz

import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.speediz.core.application.MySharePreferences
import com.example.speediz.ui.feature.authorized.vendor.map.ScreenMap
import com.example.speediz.ui.feature.unauthorized.signIn.SignInViewModel
import com.example.speediz.ui.graphs.AppNavigation
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
            val showSplashScreen = remember { mutableStateOf(true) }
            LaunchedEffect( navController) {
                navController.addOnDestinationChangedListener {
                    _, destination, _ ->
                    isLoggedIn.value = sharePreferences.getToken() != null
                }
            }
            LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(1500)
                showSplashScreen.value = false
            }
                // A surface container using the 'background' color from the theme
           SpeedizTheme {
               if (showSplashScreen.value) {
                   ScreenSplashScreen()

               } else {
                   AppNavigation(
                       navController = navController
                   )
               }
           }

        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SpeedizTheme {
        ScreenMap(modifier = Modifier)
    }
}