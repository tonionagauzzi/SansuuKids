package com.vitantonio.nagauzzi.sansuukids.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.vitantonio.nagauzzi.sansuukids.navigation.key.SansuuKidsRoute

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
}

@Composable
internal fun rememberNavigationState(
    initialRoute: SansuuKidsRoute
): NavigationState {
    return remember {
        NavigationState(mutableStateListOf(initialRoute))
    }
}
