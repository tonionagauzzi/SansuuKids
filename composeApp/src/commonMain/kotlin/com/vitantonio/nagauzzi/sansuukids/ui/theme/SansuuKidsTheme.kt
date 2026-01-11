package com.vitantonio.nagauzzi.sansuukids.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0D47A1), // Blue 900 (tone ~30)
    onPrimary = Color.White,
    secondary = Color(0xFFE65100), // Orange 900 (tone ~35)
    onSecondary = Color.White,
    tertiary = Color(0xFF1B5E20), // Green 900 (tone ~30)
    onTertiary = Color.White,
    primaryContainer = Color(0xFF1976D2), // Blue 700 (tone ~50)
    onPrimaryContainer = Color.White,
    secondaryContainer = Color(0xFFFF9800), // Orange 500 (tone ~70)
    onSecondaryContainer = Color.White,
    tertiaryContainer = Color(0xFF4CAF50), // Green 500 (tone ~55)
    onTertiaryContainer = Color.White,
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
