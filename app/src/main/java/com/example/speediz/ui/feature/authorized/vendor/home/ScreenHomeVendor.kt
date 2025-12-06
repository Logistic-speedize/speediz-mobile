package com.example.speediz.ui.feature.authorized.vendor.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.speediz.R
import com.example.speediz.ui.navigation.AuthorizedRoute


@Composable
fun ScreenHomeVendor(
    onNavigateTo: (String) -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    val viewModel = hiltViewModel<HomeVendorViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 480.dp)
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                ,
                contentAlignment = Alignment.Center
            ) {
                WelcomeUser(
                    email = when {
                        uiState.email.isBlank() -> "Unknown User"
                        else -> uiState.email
                    },
                    profile = R.drawable.img_default_profile,
                    onToProfile = onNavigateToProfile
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier.weight(1f),
//                contentAlignment = Alignment.Center
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    uiState.actions.forEach { action  ->
                        NavigateButton(
                            text = action.label,
                            imageRes = action.iconRes,
                            onClick = { onNavigateTo(action.route) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NavigateButton(
    text: String,
    imageRes: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick ,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White) ,
        shape = RoundedCornerShape(16.dp) ,
        border = BorderStroke(3.dp , MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = text,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(start = 25.dp)
                    .weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun WelcomeUser(
    email: String,
    profile: Int,
    onNotificationsClick: () -> Unit = {},
    onToProfile: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .clickable {
                    onToProfile()
                }
            ,
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = profile),
                contentDescription = "Profile",
                modifier = Modifier.fillMaxSize()
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
            ) {
                Text(
                    text = "Welcome,",
                    color = colorResource(id = R.color.neutral_gray),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 14.sp
                )
                Text(
                    text = email,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 16.sp
                )
            }

            IconButton(onClick = onNotificationsClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = "Notifications",
                    tint = colorResource(id = R.color.neutral_gray),
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}