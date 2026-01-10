package com.vitantonio.nagauzzi.sansuukids

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.vitantonio.nagauzzi.sansuukids.ui.screen.HomeScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.ModeSelectionScreen
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

sealed class Screen {
    data object Home : Screen()
    data object ModeSelection : Screen()
}

@Composable
@Preview
fun App() {
    var currentScreen: Screen by remember { mutableStateOf(Screen.Home) }

    SansuuKidsTheme {
        when (currentScreen) {
            Screen.Home -> HomeScreen(
                onStartClick = { currentScreen = Screen.ModeSelection },
                onMedalCollectionClick = { /* TODO: Navigate to Medal Collection */ },
                onSettingsClick = { /* TODO: Navigate to Settings */ }
            )
            Screen.ModeSelection -> ModeSelectionScreen(
                onAdditionClick = { /* TODO: Navigate to Level Selection */ },
                onSubtractionClick = { /* TODO: Navigate to Level Selection */ },
                onMultiplicationClick = { /* TODO: Navigate to Level Selection */ },
                onDivisionClick = { /* TODO: Navigate to Level Selection */ },
                onAllClick = { /* TODO: Navigate to Level Selection */ }
            )
        }
    }
}
