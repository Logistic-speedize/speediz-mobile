package com.example.speediz.ui.feature.authorized.delivery.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.mapbox.maps.extension.style.expressions.dsl.generated.mod

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenAccount(
    onConfirm: () -> Unit = {},
    onBackPress: () -> Unit = {},
    onClickToEdit: () -> Unit = {},
) {
    var clickToEdit by remember { mutableStateOf(false) }
    val enableToEdit = clickToEdit
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Account",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Black
                    )
                },
                actions = {
                    IconButton(onClick = {
                        clickToEdit = !clickToEdit
                    }) {
                        if (!clickToEdit) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = SPColor.onPrimary
                ),
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onBackPress() },
                    )
                }
            )
        },
        containerColor = Color.Transparent,
        modifier = Modifier.padding(16.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            // Profile Picture Section
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
                    .border(
                        width = 2.dp,
                        color = SPColor.primary,
                        shape = CircleShape
                    )
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
                    modifier = Modifier.weight(1f),
                    enable = enableToEdit

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
                label = "Shop Name",
                enable = enableToEdit
            )

            Spacer(modifier = Modifier.height(12.dp))

            CompactTextField(
                value = "",
                onValueChange = {},
                label = "Email",
                enable = enableToEdit
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
                    modifier = Modifier.weight(1f),
                    enable = enableToEdit
                )
                CompactTextField(
                    value = "",
                    onValueChange = {},
                    label = "Gender",
                    modifier = Modifier.weight(1f),
                    enable = enableToEdit
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            CompactTextField(
                value = "",
                onValueChange = {},
                label = "Address",
                enable = enableToEdit
            )

            Spacer(modifier = Modifier.height(12.dp))

            CompactTextField(
                value = "",
                onValueChange = {},
                label = "Contact",
                enable = enableToEdit
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Log Out Button
            Button(
                onClick = { /* TODO: handle log out */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SPColor.primary,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (clickToEdit) "Save" else "Log Out",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
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
