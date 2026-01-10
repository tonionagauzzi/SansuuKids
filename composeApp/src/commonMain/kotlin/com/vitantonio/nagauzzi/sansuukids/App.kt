package com.vitantonio.nagauzzi.sansuukids

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import com.vitantonio.nagauzzi.sansuukids.ui.screen.HomeScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.ModeSelectionScreen
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Serializable
data object HomeRoute : NavKey

@Serializable
data object ModeSelectionRoute : NavKey

@Composable
@Preview
fun App() {
    val backStack = remember { mutableStateListOf<NavKey>(HomeRoute) }

    SansuuKidsTheme {
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = { key ->
                when (key) {
                    HomeRoute -> NavEntry(key) {
                        HomeScreen(
                            onStartClick = { backStack.add(ModeSelectionRoute) },
                            onMedalCollectionClick = { /* TODO: Navigate to Medal Collection */ },
                            onSettingsClick = { /* TODO: Navigate to Settings */ }
                        )
                    }

                    ModeSelectionRoute -> NavEntry(key) {
                        ModeSelectionScreen(
                            onAdditionClick = { /* TODO: Navigate to Level Selection */ },
                            onSubtractionClick = { /* TODO: Navigate to Level Selection */ },
                            onMultiplicationClick = { /* TODO: Navigate to Level Selection */ },
                            onDivisionClick = { /* TODO: Navigate to Level Selection */ },
                            onAllClick = { /* TODO: Navigate to Level Selection */ }
                        )
                    }

                    else -> NavEntry(key) {}
                }
            }
        )
    }
}
