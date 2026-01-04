package com.example.speediz.ui.feature.unauthorized.signup.delivery

import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.speediz.R
import com.example.speediz.ui.feature.appwide.button.SpDatePickerInput
import com.example.speediz.ui.feature.unauthorized.signup.SignUPState
import com.example.speediz.ui.navigation.UnauthorizedRoute
import com.example.speediz.ui.theme.SpeedizTheme
import com.example.speediz.ui.utils.dateFormat
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Locale.getDefault

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScreenDeliverySignUp(
    onNavigateTo: () -> Unit,
    onBackPress: () -> Unit,
) {
    val viewModel = hiltViewModel<DeliveryVM>()
    val signUpState = viewModel.signUpUiState.collectAsState().value
    val context = LocalContext.current

    val genderOptions = listOf("Male", "Female", "Other")
    val deliveryOptions = listOf("Motorcycle", "Car", "Van", "Truck")
    var isFirstName by remember { mutableStateOf(false) }
    var isLastName by remember { mutableStateOf(false) }
    var isGender by remember { mutableStateOf(false) }
    var isDob by remember { mutableStateOf(false) }
    var isPhone by remember { mutableStateOf(false) }
    var isEmail by remember { mutableStateOf(false) }
    var isPassword by remember { mutableStateOf(false) }
    var isConfirmPassword by remember { mutableStateOf(false) }
    var isCvPath by remember { mutableStateOf(false) }
    var isDeliveryType by remember { mutableStateOf(false) }
    var isZone by remember { mutableStateOf(false) }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var expandedGender by remember { mutableStateOf(false) }
    var expandedDelivery by remember { mutableStateOf(false) }

    LaunchedEffect(
        signUpState
    ) {
        when (signUpState) {
            is SignUPState.Loading -> {
                // Show loading indicator if needed
            }
            is SignUPState.Success -> {
                Toast.makeText(
                    context,
                    "Vendor registered successfully!",
                    Toast.LENGTH_LONG
                ).show()
                onNavigateTo()
                Log.d("Navigation", "Navigating to: ${UnauthorizedRoute.SignIn.route}")
            }
            is SignUPState.Error -> {
                Toast.makeText(
                    context,
                    signUpState.message,
                    Toast.LENGTH_LONG
                ).show()
                Log.d( "ScreenDeliverySignUp", "Sign up error: ${signUpState.message}" )
            }
            else -> {
                // Handle other states if necessary
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ){ padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable { onBackPress() },
                tint = Color.Black
            )
            Image(
                painter = painterResource( id = R.drawable.ic_logo) ,
                contentDescription = "Logo" ,
                modifier = Modifier.fillMaxWidth().height(100.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )
            Text(
                text = "Sign up",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = viewModel.firstname,
                    onValueChange = {
                        if (!isFirstName) isFirstName = true
                        viewModel.onFirstnameChanged(it)
                    },
                    label = { Text("First Name") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Ascii
                    ),
                    singleLine = true,
                    supportingText = {
                        val message = viewModel.onFirstnameChanged(viewModel.firstname)
                        if (isFirstName) {
                            Text(text = message, color = Color.Red)
                        } else {
                            ""
                        }
                    }
                )
                OutlinedTextField(
                    value = viewModel.lastname,
                    onValueChange = {
                        if (!isLastName) isLastName = true
                        viewModel.onLastNameChanged(it)
                    },
                    label = { Text("Last Name") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Ascii
                    ),
                    singleLine = true,
                    supportingText = {
                        val message = viewModel.onLastNameChanged(viewModel.lastname)
                        if (isLastName) {
                            Text(text = message, color = Color.Red)
                        } else {
                            ""
                        }
                    }
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = viewModel.gender,
                        onValueChange = {
                            if (!isGender) isGender = true
                            viewModel.onGenderChanged(it)
                        },
                        label = { Text("Gender") },
                        trailingIcon = {
                            Icon(
                                imageVector = if (expandedGender)
                                    Icons.Default.KeyboardArrowUp
                                else
                                    Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                modifier = Modifier.clickable { expandedGender = !expandedGender }
                            )
                        },
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expandedGender = !expandedGender },
                        supportingText = {
                            if (isGender) {
                                val message = viewModel.onGenderChanged(viewModel.gender)
                                Text(text = message, color = Color.Red)
                            } else {
                                ""
                            }
                        }
                    )
                    DropdownMenu(
                        expanded = expandedGender,
                        onDismissRequest = { expandedGender = false },
                        modifier = Modifier
                            .focusable()
                            .background( color = Color.White)
                    ) {
                        genderOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option, color = Color.Black) },
                                onClick = {
                                    viewModel.gender = option
                                    expandedGender = false
                                },
                                modifier = Modifier.fillMaxWidth().background( color = Color.White),
                            )
                        }
                    }
                }
                Box(modifier = Modifier.weight(1f)) {
                    CVUploadField(
                        selectedCV = viewModel.nidUri,
                        onCVSelected = {
                            if (!isCvPath) isCvPath = true
                            viewModel.onNidImageChanged(it)
                        }
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    SpDatePickerInput(
                        onValueChange = {
                            if (!isDob) isDob = true
                            viewModel.onDOBChanged(dateFormat(it))
                        },
                        placeholderText = "DOB",
                        supportingText = {
                            val message = viewModel.onDOBChanged(viewModel.dob)
                            if (isDob) {
                                Text(text = message, color = Color.Red)
                            } else {
                                ""
                            }
                        }
                    )
                }
                OutlinedTextField(
                    value = viewModel.contactNumber,
                    onValueChange = {
                        if (!isPhone) isPhone = true
                        viewModel.onContactNumberChanged(it)
                    },
                    label = { Text("Contact") },
                    placeholder = { Text("phone number") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Phone
                    ),
                    singleLine = true,
                    supportingText = {
                        val message = viewModel.onContactNumberChanged(viewModel.contactNumber)
                        if (isPhone) {
                            Text(text = message, color = Color.Red)
                        } else {
                            ""
                        }
                    }
                )
            }

            OutlinedTextField(
                value = viewModel.zone,
                onValueChange = {
                    if (!isZone) isZone = true
                    viewModel.onLocationChanged(it)
                },
                label = { Text("Location") },
                placeholder = { Text("Select Location") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = viewModel.driverType,
                    onValueChange = {
                        if (!isDeliveryType) isDeliveryType = true
                        viewModel.onDriverTypeChanged(it)
                    },
                    label = { Text("Driver Type") },
                    trailingIcon = {
                        Icon(
                            imageVector = if (expandedDelivery)
                                Icons.Default.KeyboardArrowUp
                            else
                                Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier.clickable { expandedDelivery = !expandedDelivery }
                        )
                    },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expandedDelivery = !expandedDelivery },
                    supportingText = {
                        if (isDeliveryType) {
                            val message = viewModel.onDriverTypeChanged(viewModel.driverType)
                            Text(text = message, color = Color.Red)
                        } else {
                            ""
                        }
                    }
                )
                DropdownMenu(
                    expanded = expandedDelivery,
                    onDismissRequest = { expandedDelivery = false },
                    modifier = Modifier
                        .focusable()
                        .background( color = Color.White)
                ) {
                    deliveryOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option, color = Color.Black) },
                            onClick = {
                                viewModel.driverType = option
                                expandedDelivery = false
                            },
                            modifier = Modifier.fillMaxWidth().background( color = Color.White),
                        )
                    }
                }
            }
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = {
                    if (!isEmail) isEmail = true
                    viewModel.onEmailChanged(it)
                },
                label = { Text("Email Address") },
                placeholder = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                supportingText = {
                    val message = viewModel.onEmailChanged(viewModel.email)
                    if (isEmail) {
                        Text(text = message, color = Color.Red)
                    } else {
                        ""
                    }
                }
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = viewModel.password,
                    onValueChange = {
                        if (!isPassword) isPassword = true
                        viewModel.onPasswordChanged(it)
                    },
                    label = { Text("Password") },
                    placeholder = { Text("Password") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    supportingText = {
                        val message = viewModel.onPasswordChanged(viewModel.password)
                        if (isPassword) {
                            Text(text = message, color = Color.Red)
                        } else {
                            ""
                        }
                    }
                )

                OutlinedTextField(
                    value = viewModel.passwordConfirm,
                    onValueChange = {
                        if (!isConfirmPassword) isConfirmPassword = true
                        viewModel.onConfirmPasswordChanged(it)
                    },
                    label = { Text("Password") },
                    placeholder = { Text("Password") },
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    supportingText = {
                        val message = viewModel.onConfirmPasswordChanged(viewModel.passwordConfirm)
                        if (isConfirmPassword) {
                            Text(text = message, color = Color.Red)
                        } else {
                            ""
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    viewModel.deliverySignUp()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                enabled = viewModel.onValidate()
            ) {
                Text("Continue", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CVUploadField(
    selectedCV: Uri?,
    onCVSelected: (Uri) -> Unit,
    supportText: @Composable (() -> Unit)? = null,
) {
    var showSheet by remember { mutableStateOf(false) }

    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            onCVSelected(it) // store URI as String
            Log.d("CVUploadField" , "Selected CV URI: ${uri}")
            showSheet = false
        }
    }
    OutlinedTextField(
        value = "" , // leave empty because we will show the image below
        onValueChange = {} ,
        label = {
           if ( selectedCV == null ) {
               Text("Upload NID")
           } else {
               Text("Uploaded CV")
           }
       } ,
        trailingIcon = {
            if ( selectedCV == null ) {
                Icon(
                    imageVector =  Icons.Default.AddCircle,
                    contentDescription = "Upload CV",
                )
            } else {
                Icon(
                    imageVector =  Icons.Default.CheckCircle,
                    contentDescription = "Change CV",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        } ,
        readOnly = true ,
        enabled = false ,
        modifier = Modifier
            .clickable { showSheet = !showSheet }
        ,
        colors = OutlinedTextFieldDefaults.colors(
            disabledBorderColor = androidx.tv.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f) ,
            disabledTextColor = androidx.tv.material3.MaterialTheme.colorScheme.onSurface ,
            disabledLabelColor = androidx.tv.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f) ,
            disabledLeadingIconColor = androidx.tv.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f) ,
            disabledTrailingIconColor = androidx.tv.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f) ,
        ),
        supportingText = supportText
    )

    if (showSheet) {
        galleryLauncher.launch("image/*")
    }
}
