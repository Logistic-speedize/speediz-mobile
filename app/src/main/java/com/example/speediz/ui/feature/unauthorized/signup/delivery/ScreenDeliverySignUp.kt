package com.example.speediz.ui.feature.unauthorized.signup.delivery

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speediz.R
import com.example.speediz.ui.feature.appwide.button.SpDatePickerInput
import com.example.speediz.ui.theme.SpeedizTheme

@Composable
fun ScreenDeliverySignUp(
    onNavigateTo: () -> Unit,
    onBackPress: () -> Unit,
) {
    val genderOptions = listOf("Male", "Female", "Other")
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var cvPath by remember { mutableStateOf<Uri?>(null) }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var expandedGender by remember { mutableStateOf(false) }

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
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Ascii
                    ),
                    singleLine = true
                )
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Ascii
                    ),
                    singleLine = true
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = gender,
                        onValueChange = {},
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
                                    gender = option
                                    expandedGender = false
                                },
                                modifier = Modifier.fillMaxWidth().background( color = Color.White),
                            )
                        }
                    }
                }
                Box(modifier = Modifier.weight(1f)) {
                    CVUploadField(
                        selectedCV = cvPath,
                        onCVSelected = { cvPath = it }
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    SpDatePickerInput(
                        onValueChange = {
                            dob = it
                        },
                        placeholderText = "DOB"
                    )
                }
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Contact") },
                    placeholder = { Text("phone number") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Phone
                    ),
                    singleLine = true
                )
            }

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
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

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                placeholder = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    placeholder = { Text("Password") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Password") },
                    placeholder = { Text("Password") },
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onNavigateTo,
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

@Preview ( showBackground = true)
@Composable
fun PreviewScreenDeliverySignUp() {
    SpeedizTheme {
        ScreenDeliverySignUp(
            onNavigateTo = {},
            onBackPress = {}
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CVUploadField(
    selectedCV: Uri?,
    onCVSelected: (Uri) -> Unit
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
               Text("Uploaded")
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
        )
    )

    if (showSheet) {
        galleryLauncher.launch("image/*")
    }
}
