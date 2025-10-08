package com.example.speediz.ui.feature.unauthorized.signIn

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ScreenSignIn(
    onNavigateTo : () -> Unit
) {
    Text ("Sign In Screen")
    Button(onClick = onNavigateTo) {
        Text("Go to Sign Up")
    }
}