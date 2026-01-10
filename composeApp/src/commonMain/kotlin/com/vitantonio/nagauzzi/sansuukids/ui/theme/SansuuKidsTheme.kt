package com.vitantonio.nagauzzi.sansuukids.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1976D2),
    onPrimary = Color.White,
    secondary = Color(0xFFFF9800),
    onSecondary = Color.White,
    tertiary = Color(0xFF4CAF50),
    onTertiary = Color.White,
    background = Color(0xFFFFF8E1),
    surface = Color.White,
    onBackground = Color(0xFF1A1A1A),
    onSurface = Color(0xFF1A1A1A)
)

@Composable
fun SansuuKidsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}
