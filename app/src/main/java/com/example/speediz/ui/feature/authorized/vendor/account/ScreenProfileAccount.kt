package com.example.speediz.ui.feature.authorized.vendor.account

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.speediz.ui.utils.convertDateCalendar
import com.example.speediz.ui.utils.dateFormat

@Composable
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
fun ScreenProfileScreen(
    onLogOut: () -> Unit = {},
    onBackPress: () -> Unit = {},
) {
    val viewModel = hiltViewModel<VendorAccountVM>()
    val accountViewModel = hiltViewModel<SignInViewModel>()
    val profileDataState by viewModel.profileUIState.collectAsState()
    // val isLoggedOut by accountViewModel.isLoggedIn.collectAsState()
    val isLoggedOut = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchProfileData()
    }

    var clickToEdit by remember { mutableStateOf(false) }
    val enableToEdit = clickToEdit

    when (profileDataState){
        is VendorProfileState.Loading -> {
            SPLoading()
        }
        is VendorProfileState.Success -> {
            val response = profileDataState as VendorProfileState.Success
            val profileData = response.response
            val dob = convertDateCalendar(profileData.driver.dob, inputPattern = "yyyy-MM-dd", outputPattern = "dd-MM-yyyy")
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
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    CompactTextField(
                        value = profileData.driver.businessName,
                        onValueChange = {},
                        label = "Shop Name",
                        enable = enableToEdit
                    )

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
                            value = dob,
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
        is VendorProfileState.Error -> {
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