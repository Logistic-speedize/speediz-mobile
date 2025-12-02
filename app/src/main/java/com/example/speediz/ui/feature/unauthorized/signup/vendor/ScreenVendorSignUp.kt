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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.speediz.R
import com.example.speediz.core.data.vendor.SignUpVendorRequest
import com.example.speediz.ui.feature.appwide.button.SpDatePickerInput
import com.example.speediz.ui.feature.unauthorized.signup.SignUPState
import com.example.speediz.ui.navigation.UnauthorizedRoute
import com.example.speediz.ui.utils.dateFormat
import java.util.Locale.getDefault

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
    val firstName = remember {
        mutableStateOf("")
    }
    val lastName = remember {
        mutableStateOf("")
    }
    val gender = remember {
        mutableStateOf("")
    }
    val dob = remember {
        mutableStateOf("")
    }
    val phone = remember {
        mutableStateOf("")
    }
    val location= remember {
        mutableStateOf("")
    }
    val email = remember {
        mutableStateOf("")
    }
    val password= remember {
        mutableStateOf("")
    }
    val confirmPassword =remember {
        mutableStateOf("")
    }
    val businessName =remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    LaunchedEffect(signUpState) {
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
            }
            else -> {
                // Handle other states if necessary
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun onSubmit (){
        val request = SignUpVendorRequest(
            firstName = firstName.value ,
            lastName = lastName.value ,
            gender =  gender.value.lowercase(getDefault()) ,
            dob = dateFormat(dob.value) ,
            contactNumber = phone.value ,
            address = location.value ,
            email = email.value ,
            password = password.value ,
            passwordConfirm = confirmPassword.value ,
            businessName = businessName.value ,
        )
        signUpViewModel.signUpVendor(request)
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
                    value = firstName.value,
                    onValueChange = { firstName.value = it },
                    label = { Text("First Name") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                )
                OutlinedTextField(
                    value = lastName.value,
                    onValueChange = { lastName.value = it },
                    label = { Text("Last Name") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Gender Dropdown
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = gender.value,
                        onValueChange = {
                            gender.value = it
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
                            .clickable { expandedGender = !expandedGender }
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
                                    gender.value = option
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
                            dob.value = it
                        },
                        placeholderText = "DOB"
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = businessName.value,
                    onValueChange = { businessName.value = it },
                    label = { Text("Business name") },
                    placeholder = { Text("Business name") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    supportingText = {
                        Text(
                            if ( businessName.value.isEmpty()){
                                "Enter your business name"
                            } else {
                                ""
                            },
                            color = if ( businessName.value.isEmpty()){
                                Color.Red
                            } else {
                                Color.Transparent
                            }
                        )
                    }
                )
                OutlinedTextField(
                    value = phone.value,
                    onValueChange = { phone.value = it },
                    label = { Text("Contact") },
                    placeholder = { Text("phone number") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                )
            }
            OutlinedTextField(
                value = location.value,
                onValueChange = { location.value = it },
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
            )
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email Address") },
                placeholder = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = { Text("Password") },
                    placeholder = { Text("Password") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = confirmPassword.value,
                    onValueChange = { confirmPassword.value = it },
                    label = { Text("Password") },
                    placeholder = { Text("Confirm Password") },
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick ={
                    onSubmit()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text("Continue", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}

@Preview ( showBackground = true )
@Composable
fun ScreenVendorSignUpPreview() {
    ScreenVendorSignUp(
        onNavigateTo = {},
        onBackPress = {}
    )
}