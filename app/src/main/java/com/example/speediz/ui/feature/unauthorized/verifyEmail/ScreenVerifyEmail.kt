package com.example.speediz.ui.feature.unauthorized.verifyEmail

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ScreenEmailVerify(
    onNavigateTo : () -> Unit = {},
    onBackPress : () -> Unit = {},
) {
    Text( text = "Verify Email Screen" )
    Button(
        onClick = onNavigateTo,
    ){
        Text( text = "Go to Sign In" )
    }

}