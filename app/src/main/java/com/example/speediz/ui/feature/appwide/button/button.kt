package com.example.speediz.ui.feature.appwide.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.speediz.ui.theme.SpeedizTheme

@Composable
fun Button(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    title : String,

) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = title)
    }
}
@Composable
fun Button(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title : String,

    ) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(text = title)
    }
}

@Preview (showBackground = true)
@Composable
fun ButtonPreview() {
    SpeedizTheme {
        Button(
            onClick = {},
            title = "Button"
        )
    }
}