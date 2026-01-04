package com.example.speediz.ui.feature.unauthorized.signup.vendor

import android.os.Build
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.speediz.R
import com.example.speediz.core.data.vendor.SignUpVendorRequest
import com.example.speediz.ui.feature.appwide.button.SpDatePickerInput
import com.example.speediz.ui.feature.unauthorized.signup.SignUPState
import com.example.speediz.ui.utils.dateFormat

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenVendorSignUp(
    onNavigateTo : () -> Unit,
    onBackPress : () -> Unit,
) {
    val genderOptions = listOf("Male", "Female", "Other")
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var expandedGender by remember { mutableStateOf(false) }
    val signUpViewModel = hiltViewModel<VendorSignUpVM>()
    val signUpState = signUpViewModel.signUpUiState.collectAsState().value

    var isFirstName by remember { mutableStateOf(false) }
    var isLastName by remember { mutableStateOf(false) }
    var isGender by remember { mutableStateOf(false) }
    var isDob by remember { mutableStateOf(false) }
    var isPhone by remember { mutableStateOf(false) }
    var isLocation by remember { mutableStateOf(false) }
    var isEmail by remember { mutableStateOf(false) }
    var isPassword by remember { mutableStateOf(false) }
    var isConfirmPassword by remember { mutableStateOf(false) }
    var isBusinessName by remember { mutableStateOf(false) }

    val context = LocalContext.current
    LaunchedEffect(signUpState) {
        when (signUpState) {
            is SignUPState.Success -> {
                Toast.makeText(
                    context,
                    "Vendor registered successfully!",
                    Toast.LENGTH_LONG
                ).show()
                onNavigateTo()
            }
            is SignUPState.Error -> {
                Toast.makeText(
                    context,
                    signUpState.message,
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {
                // Handle other states if necessary
            }
        }
    }

    fun validate(): Boolean {
        return signUpViewModel.onValidateInput()
    }

    Scaffold(
        modifier = Modifier.wrapContentSize(),
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
                painter = painterResource( id = R.drawable.ic_logo),
                contentDescription = "Logo",
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
                    value = signUpViewModel.firstname,
                    onValueChange = {
                        if (!isFirstName) isFirstName = true
                        signUpViewModel.onFirstnameChanged(it)
                    },
                    label = { Text("First Name") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    supportingText = {
                        val message =  signUpViewModel.onFirstnameChanged(newFirstName = signUpViewModel.firstname)
                        if(isFirstName){
                            Text( text = message , color = Color.Red )
                        } else {
                            ""
                        }
                    }
                )
                OutlinedTextField(
                    value = signUpViewModel.lastname,
                    onValueChange = {
                        if (!isLastName) isLastName = true
                        signUpViewModel.onLastNameChanged(it)
                    },
                    label = { Text("Last Name") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    supportingText = {
                        val message =  signUpViewModel.onLastNameChanged(newLastName = signUpViewModel.lastname)
                        if(isLastName){
                            Text( text = message , color = Color.Red )
                        } else {
                            ""
                        }
                    }
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Gender Dropdown
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = signUpViewModel.gender,
                        onValueChange = {
                           if (!isGender) isGender = true
                            signUpViewModel.onGenderChanged(it)
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
                            val message =  signUpViewModel.onGenderChanged(newGender = signUpViewModel.gender)
                            if(isGender){
                                Text( text = message , color = Color.Red )
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
                                    signUpViewModel.gender = option
                                    expandedGender = false
                                },
                                modifier = Modifier.fillMaxWidth().background( color = Color.White),
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    SpDatePickerInput(
                        onValueChange = {
                            if (!isDob) isDob = true
                            signUpViewModel.onDOBChanged(newDOB = dateFormat(it))
                        },
                        placeholderText = "DOB",
                        supportingText = {
                            val message =  signUpViewModel.onDOBChanged(newDOB = signUpViewModel.dob)
                            if(isDob){
                                Text( text = message , color = Color.Red )
                            } else {
                                ""
                            }
                        }
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = signUpViewModel.businessName,
                    onValueChange = {
                        if (!isBusinessName) isBusinessName = true
                        signUpViewModel.onBusinessNameChanged(it)
                    },
                    label = { Text("Business name") },
                    placeholder = { Text("Business name") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    supportingText = {
                        val message =  signUpViewModel.onBusinessNameChanged(newBusinessName = signUpViewModel.businessName)
                        if(isBusinessName){
                            Text( text = message , color = Color.Red )
                        } else {
                            ""
                        }
                    }
                )
                OutlinedTextField(
                    value = signUpViewModel.phoneNumber,
                    onValueChange = {
                        if (!isPhone) isPhone = true
                        signUpViewModel.onContactNumberChanged(it)
                    },
                    label = { Text("Contact") },
                    placeholder = { Text("phone number") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    supportingText = {
                        val message =  signUpViewModel.onContactNumberChanged(newContactNumber = signUpViewModel.phoneNumber)
                        if(isPhone){
                            Text( text = message , color = Color.Red )
                        } else {
                            ""
                        }
                    }
                )
            }
            OutlinedTextField(
                value = signUpViewModel.address,
                onValueChange = {
                    if (!isLocation) isLocation = true
                    signUpViewModel.address = it
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
                singleLine = true,
                supportingText = {
                    val message =  signUpViewModel.onLocationChanged(newLocation = signUpViewModel.address)
                    if(isLocation){
                        Text( text = message , color = Color.Red )
                    } else {
                        ""
                    }
                }
            )
            OutlinedTextField(
                value = signUpViewModel.email,
                onValueChange = {
                    if (!isEmail) isEmail = true
                    signUpViewModel.onEmailChanged(it)
                },
                label = { Text("Email Address") },
                placeholder = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                supportingText = {
                    val message =  signUpViewModel.onEmailChanged(newEmail = signUpViewModel.email)
                    if(isEmail){
                        Text( text = message , color = Color.Red )
                    } else {
                        ""
                    }
                }
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = signUpViewModel.password,
                    onValueChange = {
                        if (!isPassword) isPassword = true
                        signUpViewModel.onPasswordChanged(it)
                    },
                    label = { Text("Password") },
                    placeholder = { Text("Password") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    supportingText = {
                        val message =  signUpViewModel.onPasswordChanged(newPassword = signUpViewModel.password)
                        if(isPassword){
                            Text( text = message , color = Color.Red )
                        } else {
                            ""
                        }
                    }
                )

                OutlinedTextField(
                    value = signUpViewModel.confirmPassword,
                    onValueChange = {
                        if (!isConfirmPassword) isConfirmPassword = true
                        signUpViewModel.onConfirmPasswordChanged(it)
                    },
                    label = { Text("Password") },
                    placeholder = { Text("Confirm Password") },
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    supportingText = {
                        val message =  signUpViewModel.onConfirmPasswordChanged(newConfirmPassword = signUpViewModel.confirmPassword)
                        if(isConfirmPassword){
                            Text( text = message , color = Color.Red )
                        } else {
                            ""
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick ={
                    val request = SignUpVendorRequest(
                        firstName = signUpViewModel.firstname ,
                        lastName = signUpViewModel.lastname ,
                        dob = signUpViewModel.dob,
                        businessName = signUpViewModel.businessName,
                        gender = signUpViewModel.gender.lowercase(),
                        contactNumber = signUpViewModel.phoneNumber ,
                        address = signUpViewModel.address ,
                        email = signUpViewModel.email ,
                        password = signUpViewModel.password ,
                        passwordConfirm = signUpViewModel.confirmPassword ,
                    )
                    Log.d("VendorSignUp", "Request: $request")
                    signUpViewModel.signUpVendor(request)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                enabled = validate()
            ) {
                Text("Continue", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}
