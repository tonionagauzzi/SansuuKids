package com.vitantonio.nagauzzi.sansuukids.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.SansuuKidsRoute

@Stable
internal class NavigationState(
    private val backStack: SnapshotStateList<SansuuKidsRoute>
) {
    val entries: List<SansuuKidsRoute> get() = backStack

    fun navigateTo(route: SansuuKidsRoute) {
        backStack.add(route)
    }

    fun navigateBack(): SansuuKidsRoute? {
        return backStack.removeLastOrNull()
    }

    fun popToHome() {
        while (backStack.size > 1) {
            backStack.removeLastOrNull()
        }
    }
}

@Composable
internal fun rememberNavigationState(
    initialRoute: SansuuKidsRoute
): NavigationState {
    return rememberSaveable {
        NavigationState(mutableStateListOf(initialRoute))
    }
}
