package com.example.speediz.ui.feature.appwide.textfield

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Icon
import com.example.speediz.ui.theme.SpeedizTheme

@Composable
fun CompactTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    height: Int = 80,
    enable: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    readOnly: Boolean = false,
    keyboardAction: KeyboardActions = KeyboardActions.Default,
    supportingText: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None

) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Label above the field
        Text(
            text = label,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height.dp)
        ) {
            var innerText by remember { mutableStateOf(value) }

            // Vertically center text using Box + align
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                OutlinedTextField(
                    value = innerText,
                    onValueChange = {
                        innerText = it
                        onValueChange(it)
                    },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 13.sp, color = Color.Black),
                    modifier = Modifier
                        .fillMaxWidth(),
                    enabled = enable,
                    placeholder = { Text(text = label, fontSize = 13.sp) },
                    keyboardOptions = keyboardOptions,
                    readOnly = readOnly,
                    keyboardActions = keyboardAction,
                    supportingText = supportingText,
                    visualTransformation = visualTransformation
                )
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun CompactTextFieldPreview() {
    SpeedizTheme{
        CompactTextField(
            value = "",
            onValueChange = {},
            label = "Receiver’s Phone Number",
            modifier = Modifier.padding(16.dp)
        )
    }
}

