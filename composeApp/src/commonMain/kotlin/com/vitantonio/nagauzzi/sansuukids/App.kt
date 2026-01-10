package com.vitantonio.nagauzzi.sansuukids

import androidx.compose.runtime.Composable
import com.vitantonio.nagauzzi.sansuukids.ui.screen.HomeScreen
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    SansuuKidsTheme {
        HomeScreen(
            onStartClick = { /* TODO: Navigate to Mode Selection */ },
            onMedalCollectionClick = { /* TODO: Navigate to Medal Collection */ },
            onSettingsClick = { /* TODO: Navigate to Settings */ }
        )
    }
}
