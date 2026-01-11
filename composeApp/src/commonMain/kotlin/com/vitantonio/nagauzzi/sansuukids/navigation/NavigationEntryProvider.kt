package com.vitantonio.nagauzzi.sansuukids.navigation

import androidx.navigation3.runtime.NavEntry
import com.vitantonio.nagauzzi.sansuukids.navigation.key.SansuuKidsRoute
import com.vitantonio.nagauzzi.sansuukids.navigation.key.HomeRoute
import com.vitantonio.nagauzzi.sansuukids.navigation.key.ModeSelectionRoute
import com.vitantonio.nagauzzi.sansuukids.ui.screen.HomeScreen
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
                onAdditionClick = { /* TODO: Navigate to Level Selection */ },
                onSubtractionClick = { /* TODO: Navigate to Level Selection */ },
                onMultiplicationClick = { /* TODO: Navigate to Level Selection */ },
                onDivisionClick = { /* TODO: Navigate to Level Selection */ },
                onAllClick = { /* TODO: Navigate to Level Selection */ }
            )
        }
    }
}
