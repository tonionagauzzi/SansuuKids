package com.vitantonio.nagauzzi.sansuukids.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.ResultRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.SansuuKidsRoute
import kotlinx.serialization.json.Json

@Stable
internal class NavigationState(
    private val backStack: SnapshotStateList<SansuuKidsRoute>
) {
    val entries: List<SansuuKidsRoute> get() = backStack

    fun navigateTo(route: SansuuKidsRoute) {
        backStack.add(route)
    }

    fun navigateBack(): SansuuKidsRoute? {
        // 完了済みのクイズ画面に戻るとメダル加算と結果画面への遷移が再実行されるため、
        // 結果画面では戻る操作を無効にする
        if (backStack.lastOrNull() is ResultRoute) {
            return null
        }
        return backStack.removeLastOrNull()
    }

    fun popToHome() {
        while (backStack.size > 1) {
            backStack.removeLastOrNull()
        }
    }

    companion object {
        fun saver(): Saver<NavigationState, String> = Saver(
            save = { state ->
                Json.encodeToString(state.entries.toList())
            },
            restore = { json ->
                val routes = Json.decodeFromString<List<SansuuKidsRoute>>(json)
                NavigationState(mutableStateListOf(*routes.toTypedArray()))
            }
        )
    }
}

@Composable
internal fun rememberNavigationState(
    initialRoute: SansuuKidsRoute
): NavigationState {
    return rememberSaveable(saver = NavigationState.saver()) {
        NavigationState(mutableStateListOf(initialRoute))
    }
}
