package com.example.speediz.ui.feature.authorized.vendor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.Navigator

@Composable
fun ScreenHomeVendor(
    onNavigateTo : (String) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Text("Home Vendor")
    }
}