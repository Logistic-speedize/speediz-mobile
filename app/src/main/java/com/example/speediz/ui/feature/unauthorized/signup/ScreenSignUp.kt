package com.example.speediz.ui.feature.unauthorized.signup

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ScreenSignUp(
    onNavigateTo : () -> Unit,
    onBackPress : () -> Unit,
) {
    Text(
        text = "Sign Up Screen"
    )
    Button(
        onClick = onNavigateTo
    ){
        Text(
            text = "Go to Sign In"
        )
    }
}