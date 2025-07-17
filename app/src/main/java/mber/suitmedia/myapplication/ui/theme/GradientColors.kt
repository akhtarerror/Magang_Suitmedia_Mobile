package mber.suitmedia.myapplication.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Orange gradient colors
val OrangeGradientColors = listOf(
    Color(0xFFFF6B35),
    Color(0xFFFF8E53),
    Color(0xFFFFA726),
    Color(0xFFFFB74D)
)

val OrangeGradientBrush = Brush.verticalGradient(
    colors = OrangeGradientColors
)

val OrangeGradientHorizontalBrush = Brush.horizontalGradient(
    colors = OrangeGradientColors
)