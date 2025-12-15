package com.example.speediz.ui.feature.authorized.delivery.account

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.speediz.R
import com.example.speediz.ui.feature.appwide.button.SPDialog
import com.example.speediz.ui.feature.appwide.button.SPLoading
import com.example.speediz.ui.feature.appwide.textfield.CompactTextField
import com.example.speediz.ui.feature.unauthorized.signIn.SignInViewModel
import com.example.speediz.ui.theme.SPColor
import com.example.speediz.ui.theme.SpeedizTheme
import com.example.speediz.ui.utils.dateFormat
import com.mapbox.maps.extension.style.expressions.dsl.generated.mod

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenAccount(
    onLogOut: () -> Unit = {},
    onBackPress: () -> Unit = {},
) {
    val viewModel = hiltViewModel<AccountVM>()
    val accountViewModel = hiltViewModel<SignInViewModel>()
    val profileDataState by viewModel.profileUIState.collectAsState()
   // val isLoggedOut by accountViewModel.isLoggedIn.collectAsState()
    var isLoggedOut = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchProfileData()
    }

    var clickToEdit by remember { mutableStateOf(false) }
    val enableToEdit = clickToEdit
    when (profileDataState){
        is DeliveryProfileState.Loading -> {
            SPLoading()
        }
        is DeliveryProfileState.Success -> {
            val response = profileDataState as DeliveryProfileState.Success
            val profileData = response.response
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
                        AsyncImage(
                            model = profileData.driver.imageProfile,
                            contentDescription = "Profile photo",
                            placeholder = painterResource(R.drawable.ic_profile_fallback),
                            error = painterResource(R.drawable.ic_profile_fallback),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
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
                            value = profileData.driver.firstName,
                            onValueChange = {},
                            label = "First Name",
                            modifier = Modifier.weight(1f),
                            enable = enableToEdit

                        )
                        CompactTextField(
                            value = profileData.driver.lastName,
                            onValueChange = {},
                            label = "Last Name",
                            modifier = Modifier.weight(1f),
                            enable = enableToEdit
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    CompactTextField(
                        value = profileData.user.email,
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
                            value = dateFormat(profileData.driver.dob),
                            onValueChange = {},
                            label = "Date of Birth",
                            modifier = Modifier.weight(1f),
                            enable = enableToEdit
                        )
                        CompactTextField(
                            value = profileData.driver.gender,
                            onValueChange = {},
                            label = "Gender",
                            modifier = Modifier.weight(1f),
                            enable = enableToEdit
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    CompactTextField(
                        value = profileData.driver.address,
                        onValueChange = {},
                        label = "Address",
                        enable = enableToEdit
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CompactTextField(
                        value = profileData.driver.contactNumber,
                        onValueChange = {},
                        label = "Contact",
                        enable = enableToEdit
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Log Out Button
                    Button(
                        onClick = {
                            isLoggedOut.value = true
                        },
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
        is DeliveryProfileState.Error -> {
            // You can show an error message here if needed
        }
    }

    if (isLoggedOut.value){
        SPDialog(
            title = "Confirm Log Out",
            description = "Are you sure you want to log out?",
            onConfirm = {
                accountViewModel.signOut()
                onLogOut()
            },
            onDismissRequest = {
                isLoggedOut.value = false
            },
            isEnablePassValue = false
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ScreenAccountPreview() {
    SpeedizTheme {
        ScreenAccount()
    }
}
