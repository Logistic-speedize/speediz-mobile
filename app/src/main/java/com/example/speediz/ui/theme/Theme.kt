package com.example.speediz.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    background = LightBackground,
    surface = LightSurface,
    primary = LightOrange,
    onPrimary = DarkOrange,
    onSecondary = LightGray,
    onTertiary = White,
    onBackground = LightTextFieldGray,
    onSurface = DarkSurface,
)

private val LightColorScheme = lightColorScheme(
    background = LightBackground,
    surface = LightSurface,
    primary = LightOrange,
    onPrimary = DarkOrange,
    onSecondary = LightGray,
    onTertiary = White,
    onBackground = LightTextFieldGray,
    onSurface = DarkSurface,
)

@Composable
fun SpeedizTheme(
    colorScheme: ColorScheme = LightColorScheme,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}