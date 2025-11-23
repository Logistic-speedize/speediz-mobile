package com.example.speediz.ui.feature.appwide.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Icon
import androidx.tv.material3.Text
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate

@Composable
fun SelectDate(
    value: String = "Select Dates",
    modifier : Modifier
) {
    TextField(
        value = value ,
        onValueChange = { /* No-op since this is a read-only field */ } ,
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Blue, RoundedCornerShape(8.dp)),
        readOnly = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Date Icon"
            )
        },
        textStyle = androidx.compose.ui.text.TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        ),
        placeholder = {
            Text(
                text = "Select Dates",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        },
    )
}
