package com.vitantonio.nagauzzi.sansuukids.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey

@Stable
class NavigationState(
    private val backStack: SnapshotStateList<NavKey>
) {
    val entries: List<NavKey> get() = backStack

    fun navigateTo(route: NavKey) {
        backStack.add(route)
    }

    fun navigateBack(): NavKey? {
        return backStack.removeLastOrNull()
    }
}

@Composable
fun rememberNavigationState(
    initialRoute: NavKey
): NavigationState {
    return remember {
        NavigationState(mutableStateListOf(initialRoute))
    }
}
