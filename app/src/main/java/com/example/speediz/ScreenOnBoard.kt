package com.example.speediz

import android.R.attr.spacing
import android.provider.CalendarContract
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.speediz.ui.feature.appwide.button.Button
import com.example.speediz.ui.theme.FontSize
import com.example.speediz.ui.theme.spacing

@Composable
fun OnBoardScreen(
    navController : NavController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_on_board),
                contentDescription = "OnBoard Image",
                modifier = Modifier.fillMaxSize()
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer( modifier = Modifier.padding(52.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "OnBoard Image",
                modifier = Modifier.fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
            )
            Spacer( modifier = Modifier.padding(52.dp))
            Text(
                text = "Quick Delivery" ,
                modifier = Modifier.padding(4.dp).align(alignment = Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = FontSize.large,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Our intelligent route planning software guarantees the optimal path for each delivery.",
                modifier = Modifier.padding(4.dp).align(alignment = Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = FontSize.medium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer( modifier = Modifier.padding(24.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 16.dp)
                    .background(color = MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.medium)
                ,
                onClick = {},
               title = "Get Started"
            )
        }
    }
}
@Preview (showBackground = true)
@Composable
fun PreviewOnBoardScreen() {
    OnBoardScreen(navController = NavController(LocalContext.current))
}