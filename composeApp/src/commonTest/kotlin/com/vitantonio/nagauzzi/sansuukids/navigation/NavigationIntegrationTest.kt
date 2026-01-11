package com.vitantonio.nagauzzi.sansuukids.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import com.vitantonio.nagauzzi.sansuukids.navigation.key.HomeRoute
import com.vitantonio.nagauzzi.sansuukids.navigation.key.ModeSelectionRoute
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class NavigationIntegrationTest {

    @Test
    fun ホーム画面でスタートボタンを押すとモード選択画面に遷移する() = runComposeUiTest {
        val backStack = mutableStateListOf<NavKey>(HomeRoute)
        val navigationState = NavigationState(backStack)

        setContent {
            SansuuKidsTheme {
                NavDisplay(
                    backStack = navigationState.entries,
                    onBack = { navigationState.navigateBack() },
                    entryProvider = { key -> navigationEntryProvider(key, navigationState) }
                )
            }
        }

        onNodeWithTag("start_button").assertIsDisplayed()
        onNodeWithTag("start_button").performClick()

        assertEquals(2, navigationState.entries.size)
        assertEquals(ModeSelectionRoute, navigationState.entries.last())
        onNodeWithTag("addition_button").assertIsDisplayed()
    }

    @Test
    fun モード選択画面で戻ると前のホーム画面に戻る() = runComposeUiTest {
        val backStack = mutableStateListOf<NavKey>(HomeRoute, ModeSelectionRoute)
        val navigationState = NavigationState(backStack)

        setContent {
            SansuuKidsTheme {
                NavDisplay(
                    backStack = navigationState.entries,
                    onBack = { navigationState.navigateBack() },
                    entryProvider = { key -> navigationEntryProvider(key, navigationState) }
                )
            }
        }

        onNodeWithTag("addition_button").assertIsDisplayed()

        navigationState.navigateBack()
        waitForIdle()

        assertEquals(1, navigationState.entries.size)
        assertEquals(HomeRoute, navigationState.entries.last())
        onNodeWithTag("start_button").assertIsDisplayed()
    }

    @Test
    fun ホーム画面が初期画面として表示される() = runComposeUiTest {
        val backStack = mutableStateListOf<NavKey>(HomeRoute)
        val navigationState = NavigationState(backStack)

        setContent {
            SansuuKidsTheme {
                NavDisplay(
                    backStack = navigationState.entries,
                    onBack = { navigationState.navigateBack() },
                    entryProvider = { key -> navigationEntryProvider(key, navigationState) }
                )
            }
        }

        onNodeWithTag("start_button").assertIsDisplayed()
        onNodeWithTag("medal_collection_button").assertIsDisplayed()
        onNodeWithTag("settings_button").assertIsDisplayed()

        assertEquals(1, navigationState.entries.size)
        assertEquals(HomeRoute, navigationState.entries.first())
    }

    @Test
    fun 往復のナビゲーションが正しく動作する() = runComposeUiTest {
        val backStack = mutableStateListOf<NavKey>(HomeRoute)
        val navigationState = NavigationState(backStack)

        setContent {
            SansuuKidsTheme {
                NavDisplay(
                    backStack = navigationState.entries,
                    onBack = { navigationState.navigateBack() },
                    entryProvider = { key -> navigationEntryProvider(key, navigationState) }
                )
            }
        }

        onNodeWithTag("start_button").assertIsDisplayed()

        onNodeWithTag("start_button").performClick()
        onNodeWithTag("addition_button").assertIsDisplayed()
        assertEquals(2, navigationState.entries.size)

        navigationState.navigateBack()
        waitForIdle()
        onNodeWithTag("start_button").assertIsDisplayed()
        assertEquals(1, navigationState.entries.size)

        onNodeWithTag("start_button").performClick()
        onNodeWithTag("addition_button").assertIsDisplayed()
        assertEquals(2, navigationState.entries.size)
    }
}
