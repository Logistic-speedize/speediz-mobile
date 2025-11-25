package com.example.speediz.ui.feature.authorized.delivery.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speediz.R
import com.example.speediz.ui.feature.appwide.textfield.CompactTextField
import com.example.speediz.ui.theme.SPColor
import com.example.speediz.ui.theme.SpeedizTheme

@Composable
fun ScreenAccount() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { /* TODO */ }
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = "Edit",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { /* TODO */ }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Profile Picture Section
        Box(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_profile_fallback),
                contentDescription = "Profile photo",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Text(
            text = "Your Information",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Name Fields in a single row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CompactTextField(
                value = "",
                onValueChange = {},
                label = "First Name",
                modifier = Modifier.weight(1f)
            )
            CompactTextField(
                value = "",
                onValueChange = {},
                label = "Last Name",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        CompactTextField(
            value = "",
            onValueChange = {},
            label = "Shop Name"
        )

        Spacer(modifier = Modifier.height(12.dp))

        CompactTextField(
            value = "",
            onValueChange = {},
            label = "Email"
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Date of Birth & Gender row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CompactTextField(
                value = "",
                onValueChange = {},
                label = "Date of Birth",
                modifier = Modifier.weight(1f)
            )
            CompactTextField(
                value = "",
                onValueChange = {},
                label = "Gender",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        CompactTextField(
            value = "",
            onValueChange = {},
            label = "Address"
        )

        Spacer(modifier = Modifier.height(12.dp))

        CompactTextField(
            value = "",
            onValueChange = {},
            label = "Contact"
        )

        Spacer(modifier = Modifier.weight(1f))

        // Log Out Button
        Button(
            onClick = { /* TODO: handle log out */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SPColor.primary,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Log Out",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenAccountPreview() {
    SpeedizTheme {
        ScreenAccount()
    }
}
