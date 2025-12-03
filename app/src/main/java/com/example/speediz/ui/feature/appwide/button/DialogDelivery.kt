package com.example.speediz.ui.feature.appwide.button

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.speediz.ui.theme.SPColor
import com.example.speediz.ui.theme.SpeedizTheme
import com.mapbox.maps.extension.style.expressions.dsl.generated.mod

@Composable
fun DialogDelivery(
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit,
    title: String = "Enter Reason",
    description: String = "Please provide a reason before proceeding:",
    onChangeValue: (String) -> Unit = { },
    isEnablePassValue: Boolean
) {

    val reason = remember { mutableStateOf("") }
    val isEnableInput = remember { mutableStateOf<Boolean?>(isEnablePassValue) }
    val context = LocalContext.current
    AlertDialog(
        modifier = Modifier.fillMaxWidth() ,
        containerColor = MaterialTheme.colorScheme.surface ,
        onDismissRequest = { onDismissRequest() } ,
        title = { Text(text = title) } ,
        text = {
            Column {
                Text(text = description)
                Spacer(modifier = Modifier.height(8.dp))
                if ( isEnableInput.value == true){
                    OutlinedTextField(
                        value = reason.value,
                        onValueChange = {
                            reason.value = it
                            onChangeValue(it)
                        },
                        placeholder = { Text("Enter reason...") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                            .border(
                                BorderStroke(1.dp, SPColor.primary),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .background(
                                SPColor.surface, shape = RoundedCornerShape(12.dp)
                            ),
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    ""
                }
            }
        } ,
        confirmButton = {
            Button(
                onClick = {
                   if (isEnableInput.value == true){
                       if (reason.value.isNotEmpty()) {
                           onConfirm(reason.value)
                           Log.d("DialogDelivery" , "Confirm clicked with reason: ${reason.value}" )
                            onDismissRequest()
                       } else {
                           Toast.makeText(
                               context,
                               "Please enter a reason before confirming." ,
                               Toast.LENGTH_SHORT
                           ).show()
                           return@Button
                       }
                     } else {
                         onConfirm("")
                       onDismissRequest()
                   }
                }
            ) {
                Text("Confirm", color = MaterialTheme.colorScheme.surface)
            }
        } ,
        dismissButton = {
            OutlinedButton(
                onClick = { onDismissRequest() },
                border = BorderStroke(1.dp , MaterialTheme.colorScheme.primary)
            ) {
                Text("Cancel")
            }
        }
    )
}
@Composable
fun SPDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit,
    title: String = "Enter Reason",
    description: String = "Please provide a reason before proceeding:",
    onChangeValue: (String) -> Unit = { },
    isEnablePassValue: Boolean
) {

    val reason = remember { mutableStateOf("") }
    val isEnableInput = remember { mutableStateOf<Boolean?>(isEnablePassValue) }
    val context = LocalContext.current
    AlertDialog(
        modifier = Modifier.fillMaxWidth() ,
        containerColor = MaterialTheme.colorScheme.surface ,
        onDismissRequest = { onDismissRequest() } ,
        title = { Text(text = title) } ,
        text = {
            Column {
                Text(text = description)
                Spacer(modifier = Modifier.height(8.dp))
                if ( isEnableInput.value == true){
                    OutlinedTextField(
                        value = reason.value,
                        onValueChange = {
                            reason.value = it
                            onChangeValue(it)
                        },
                        placeholder = { Text("Enter reason...") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                            .border(
                                BorderStroke(1.dp, SPColor.primary),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .background(
                                SPColor.surface, shape = RoundedCornerShape(12.dp)
                            ),
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    ""
                }
            }
        } ,
        confirmButton = {
            Button(
                onClick = {
                    if (isEnableInput.value == true){
                        if (reason.value.isNotEmpty()) {
                            onConfirm(reason.value)
                            Log.d("DialogDelivery" , "Confirm clicked with reason: ${reason.value}" )
                            onDismissRequest()
                        } else {
                            Toast.makeText(
                                context,
                                "Please enter a reason before confirming." ,
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Button
                        }
                    } else {
                        onConfirm("")
                        onDismissRequest()
                    }
                }
            ) {
                Text("Confirm", color = MaterialTheme.colorScheme.surface)
            }
        } ,
        dismissButton = {
            OutlinedButton(
                onClick = { onDismissRequest() },
                border = BorderStroke(1.dp , MaterialTheme.colorScheme.primary)
            ) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewDialogDelivery() {
}
