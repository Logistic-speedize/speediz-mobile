package com.example.speediz.ui.feature.appwide.textfield

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CompactTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    height: Int = 60,
    enable: Boolean = true,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Label above the field
        Text(
            text = label,
            fontSize = 13.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(6.dp))
                .padding(horizontal = 8.dp)
        ) {
            var innerText by remember { mutableStateOf(value) }

            // Vertically center text using Box + align
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    value = innerText,
                    onValueChange = {
                        innerText = it
                        onValueChange(it)
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 13.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enable
                )

                if (innerText.isEmpty()) {
                    Text(
                        text = label,
                        fontSize = 13.sp,
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}

