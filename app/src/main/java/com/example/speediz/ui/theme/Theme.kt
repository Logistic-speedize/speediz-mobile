package com.example.speediz.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
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
    primary = LightOrgange,
    onPrimary = DarkOrange,
    onSecondary = LightGray,
    onTertiary = White,
    onBackground = LightTextFieldGray,
    onSurface = DarkSurface,
)

private val LightColorScheme = lightColorScheme(
    background = LightBackground,
    surface = LightSurface,
    primary = LightOrgange,
    onPrimary = DarkOrange,
    onSecondary = LightGray,
    onTertiary = White,
    onBackground = LightTextFieldGray,
    onSurface = DarkSurface,
)

@Composable
fun SpeedizTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
