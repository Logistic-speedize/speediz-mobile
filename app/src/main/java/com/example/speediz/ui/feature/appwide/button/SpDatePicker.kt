// Kotlin
package com.example.speediz.ui.feature.appwide.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import java.text.SimpleDateFormat
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpDatePickerInput(
    placeholderText: String = "",
    formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()),
    onValueChange: (String) -> Unit = {}
) {
    var dateText by remember { mutableStateOf("") }
    var showSheet by remember { mutableStateOf(false) }

    // TextField to open sheet
    OutlinedTextField(
        value = dateText ,
        onValueChange = {} ,
        label = {
            Text(
                text = placeholderText
            )
       } ,
        enabled = false ,
        singleLine = true ,
        modifier = Modifier.fillMaxWidth()
            .clickable {
            showSheet = !showSheet // open sheet on click
        } ,
        colors = OutlinedTextFieldDefaults.colors(
            disabledBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
        )
    )

    // Only show the bottom sheet if showSheet == true
    if (showSheet) {
        BottomSheetDatePicker(
            onDateSelected = { calendar ->
                dateText = formatter.format(calendar.time)
                onValueChange(dateText)
                showSheet = false // close sheet after selection
            },
            onDismissRequest = { showSheet = false } // close sheet if dismissed
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDatePicker(
    onDateSelected: (Calendar) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val datePickerState = rememberDatePickerState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val currentDate = datePickerState.selectedDateMillis?.let {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = it
        }
        val formatter = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        formatter.format(calendar.time)
    } ?: Locale.getDefault().let {
        val today = Calendar.getInstance()
        val formatter = SimpleDateFormat("MMMM dd, yyyy", it)
        formatter.format(today.time)
    }
    ModalBottomSheet(
        onDismissRequest = {
            onDismissRequest()
        } ,
        sheetState = sheetState,
        modifier = Modifier.fillMaxWidth() ,
        containerColor = MaterialTheme.colorScheme.surface ,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier.fillMaxWidth(),
                headline = {
                    Text(
                        text = currentDate,
                        style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                    )
                },
                title = {
                    Text(
                        text = "Select Date",
                        style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                    )
                }
            )
            Button(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val calendar = Calendar.getInstance().apply {
                            timeInMillis = millis
                        }
                        onDateSelected(calendar)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirm", color = androidx.compose.material3.MaterialTheme.colorScheme.surface)
            }
        }
    }
}



