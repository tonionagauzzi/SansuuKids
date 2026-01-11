package com.vitantonio.nagauzzi.sansuukids.ui.navigation

import androidx.navigation3.runtime.NavEntry
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.HomeRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.LevelSelectionRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.ModeSelectionRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.SansuuKidsRoute
import com.vitantonio.nagauzzi.sansuukids.ui.screen.HomeScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.LevelSelectionScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.ModeSelectionScreen

internal fun navigationEntryProvider(
    key: SansuuKidsRoute,
    navigationState: NavigationState
): NavEntry<SansuuKidsRoute> {
    return when (key) {
        HomeRoute -> NavEntry(key) {
            HomeScreen(
                onStartClick = { navigationState.navigateTo(ModeSelectionRoute) },
                onMedalCollectionClick = { /* TODO: Navigate to Medal Collection */ },
                onSettingsClick = { /* TODO: Navigate to Settings */ }
            )
        }

        ModeSelectionRoute -> NavEntry(key) {
            ModeSelectionScreen(
                onAdditionClick = { navigationState.navigateTo(LevelSelectionRoute(Mode.ADDITION)) },
                onSubtractionClick = { navigationState.navigateTo(LevelSelectionRoute(Mode.SUBTRACTION)) },
                onMultiplicationClick = { navigationState.navigateTo(LevelSelectionRoute(Mode.MULTIPLICATION)) },
                onDivisionClick = { navigationState.navigateTo(LevelSelectionRoute(Mode.DIVISION)) },
                onAllClick = { navigationState.navigateTo(LevelSelectionRoute(Mode.ALL)) }
            )
        }

        is LevelSelectionRoute -> NavEntry(key) {
            LevelSelectionScreen(
                onEasyClick = { /* TODO: Navigate to Quiz with key.mode and Level.EASY */ },
                onNormalClick = { /* TODO: Navigate to Quiz with key.mode and Level.NORMAL */ },
                onDifficultClick = { /* TODO: Navigate to Quiz with key.mode and Level.DIFFICULT */ }
            )
        }
    }
}
