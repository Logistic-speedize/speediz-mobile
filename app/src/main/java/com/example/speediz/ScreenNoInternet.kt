package com.example.speediz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ScreenNoInternet() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = androidx.compose.ui.res.painterResource(id = R.drawable.ic_wifi_off),
                    contentDescription = "No Internet Connection",
                    modifier = Modifier.size(100.dp),
                    alignment = Alignment.Center,
                )
            }
            Spacer( modifier = Modifier.padding(16.dp) )
            Text(
                text = "No Internet Connection",
                style = MaterialTheme.typography.headlineMedium,
            )
            Text(
                text = "Please check your network settings and try again.",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}