package com.example.speediz.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speediz.ui.theme.FontSize

val Shapes = Shapes(
    extraSmall = androidx.compose.foundation.shape.RoundedCornerShape(4.dp),
    small = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
    medium = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
    large = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
    extraLarge = androidx.compose.foundation.shape.RoundedCornerShape(32.dp)
)

val fonSize = FontSize
val spacing = Spacing
val padding = Padding
val margin = Margin
val iconSize = IconSize
object FontSize {
    val small = 12.sp
    val medium = 16.sp
    val large = 20.sp
}
object Spacing {
    val small = 4.dp
    val medium = 8.dp
    val large = 16.dp
}
object Padding {
    val small = 4.dp
    val medium = 8.dp
    val large = 16.dp
}
object Margin {
    val small = 4.dp
    val medium = 8.dp
    val large = 16.dp
}
object IconSize {
    val small = 16.dp
    val medium = 24.dp
    val large = 32.dp
}
object CornerRadius {
    val small = 4.dp
    val medium = 8.dp
    val large = 16.dp
}
object Elevation {
    val small = 2.dp
    val medium = 4.dp
    val large = 8.dp
}
object BorderWidth {
    val thin = 1.dp
    val medium = 2.dp
    val thick = 4.dp
}
object AnimationDuration {
    val short = 100
    val medium = 300
    val long = 500
}
object AnimationDelay {
    val short = 50
    val medium = 150
    val long = 250
}
object LineHeight {
    val small = 16.sp
    val medium = 20.sp
    val large = 24.sp
}
val localFontSize = compositionLocalOf { fonSize }
val MaterialTheme.fontSize: FontSize
    @Composable
    get() = localFontSize.current
val localSpacing = compositionLocalOf { spacing }
val MaterialTheme.spacing: Spacing
    @Composable
    get() = localSpacing.current
val localPadding = compositionLocalOf { padding }
val MaterialTheme.padding: Padding
    @Composable
    get() = localPadding.current