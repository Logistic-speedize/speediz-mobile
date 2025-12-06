// kotlin
package com.example.speediz.ui.feature.authorized.vendor.packageManagement.create

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.tv.material3.Icon
import androidx.tv.material3.Text
import com.example.speediz.core.data.vendor.CreatePackageRequest
import com.example.speediz.ui.feature.appwide.textfield.CompactTextField
import com.example.speediz.ui.theme.SpeedizTheme
import com.example.speediz.ui.utils.SpeedizPackageType
import com.example.speediz.ui.utils.geocodeAddress

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenCreatePackage(
    onBack: () -> Unit = {},
    onCreate: () -> Unit = {}
) {
    val viewModel = hiltViewModel<CreatePackageViewModel>()
    val uiState by viewModel.createPackageUIState.collectAsState()

    var phone by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf("") }
    var packageExpanded by remember { mutableStateOf(false) }
    var location by rememberSaveable { mutableStateOf("") }
    var latitude by rememberSaveable { mutableStateOf("") }
    var longitude by rememberSaveable { mutableStateOf("") }
    val currentLocal = LocalContext.current

    val packageTypeList = listOf(
        SpeedizPackageType.Clothing,
        SpeedizPackageType.Electronics,
        SpeedizPackageType.Food,
        SpeedizPackageType.Documents,
        SpeedizPackageType.Supplies,
        SpeedizPackageType.Other,
    )
    var selectedPackageType by remember { mutableStateOf(packageTypeList.first()) }
    var isGetLocation by remember { mutableStateOf(false) }
    LaunchedEffect(location) {
        val result = geocodeAddress(currentLocal, location)
        if (result != null) {
            latitude = result.first.toString()
            longitude = result.second.toString()
            Log.d( "Geocode", "Latitude: ${result.first}, Longitude: ${result.second}" )
        }
    }

    LaunchedEffect(uiState) {
        when(uiState) {
            is CreatePackageUIState.Success -> {
                onCreate()
            }
            else -> {
                Toast.makeText(
                    currentLocal,
                    (uiState as? CreatePackageUIState.Error)?.message ?: "",
                    Toast.LENGTH_SHORT
                )
            }
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Create Package",
                        style = androidx.compose.ui.text.TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 20.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
        ) {
            Spacer(Modifier.height(12.dp))

            // Phone number
            CompactTextField(
                label = "Receiver’s Phone Number",
                value = phone,
                onValueChange = {
                    phone = it
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions =  KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            Spacer(Modifier.height(12.dp))

            // Receiver name
            CompactTextField(
                label = "Receiver’s Name",
                value = name,
                onValueChange = {
                    name = it
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions =  KeyboardOptions(
                    keyboardType = KeyboardType.Ascii
                )
            )

            Spacer(Modifier.height(12.dp))

            // Package Type (Dropdown)
            Text(text = "Package Type:")
            Spacer(Modifier.height(6.dp))

            Box {
                OutlinedTextField(
                    value = selectedPackageType.type,
                    onValueChange = { },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { packageExpanded = !packageExpanded }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                    }
                )

                DropdownMenu(
                    expanded = packageExpanded,
                    onDismissRequest = { packageExpanded = false }
                ) {
                    packageTypeList.forEach { packageType ->
                        DropdownMenuItem(
                            text = { Text(packageType.type) },
                            onClick = {
                                selectedPackageType = packageType
                                packageExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // Price
            CompactTextField(
                label = "Package Price",
                value = price,
                onValueChange = {
                    price = it
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            Spacer(Modifier.height(12.dp))

            // Location
            CompactTextField(
                label = "Customer Location",
                value = location,
                onValueChange = {
                    location = it
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                   imeAction = ImeAction.Done
                )
            )

            Spacer(Modifier.height(16.dp))
//            Text(text = "Generate Location", fontWeight = FontWeight.SemiBold)
//            Spacer(Modifier.height(12.dp))
//
//            // Latitude
//            CompactTextField(
//                label = "Receiver's Latitude",
//                value = latitude,
//                onValueChange = {
//                    latitude = it
//                },
//                modifier = Modifier.fillMaxWidth(),
//                keyboardOptions =  KeyboardOptions(
//                    keyboardType = KeyboardType.Number
//                )
//            )

//            Spacer(Modifier.height(12.dp))
//
//            // Longitude
//            CompactTextField(
//                label = "Receiver's Longitude",
//                value = longitude,
//                onValueChange = {
//                    longitude = it
//                },
//                modifier = Modifier.fillMaxWidth(),
//                keyboardOptions =  KeyboardOptions(
//                    keyboardType = KeyboardType.Number
//                )
//            )

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.createPackage(
                        CreatePackageRequest(
                            price = price.toDouble(),
                            customerFirstName = name,
                            customerLastName = name,
                            customerPhone = phone,
                            customerLocation = location,
                            customerLatitude = latitude.toDoubleOrNull() ?: 0.0,
                            customerLongitude = longitude.toDoubleOrNull() ?: 0.0
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3B82F6)
                ),
            ) {
                Text("Create", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReceiverInfoScreenPreview() {
    SpeedizTheme {
        ScreenCreatePackage()
    }
}
