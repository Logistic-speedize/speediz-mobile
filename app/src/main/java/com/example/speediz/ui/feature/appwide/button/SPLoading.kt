package com.example.speediz.ui.feature.appwide.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.isTraceInProgress
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mapbox.maps.extension.style.layers.generated.circleLayer

@Composable
fun SPLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent) , // optional semi-transparent background
        contentAlignment = Alignment.Center
    ) {
      CircularProgressIndicator(
          color = MaterialTheme.colorScheme.primary
      )
    }
}
