package com.example.speediz.ui.feature.unauthorized.forgotPassword

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ScreenForgotPassword(
    onNavigateTo : () -> Unit = {},
    onBackPress : () -> Unit = {},
) {
    Text ( text = "Forgot Password Screen" )
    Button(
        onClick = onNavigateTo,
    ){
        Text( text = "Go to Sign In" )
    }

}