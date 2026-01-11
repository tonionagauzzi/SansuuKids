package com.vitantonio.nagauzzi.sansuukids.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.navigation3.ui.NavDisplay
import com.vitantonio.nagauzzi.sansuukids.navigation.key.SansuuKidsRoute
import com.vitantonio.nagauzzi.sansuukids.navigation.key.HomeRoute
import com.vitantonio.nagauzzi.sansuukids.navigation.key.ModeSelectionRoute
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class NavigationIntegrationTest {

    @Test
    fun ホーム画面が初期画面として表示される() = runComposeUiTest {
        // Given: ホーム画面を初期画面として設定する
        val backStack = mutableStateListOf<SansuuKidsRoute>(HomeRoute)
        val navigationState = NavigationState(backStack)

        setContent {
            SansuuKidsTheme {
                NavDisplay(
                    backStack = navigationState.entries,
                    onBack = { navigationState.navigateBack() },
                    entryProvider = { key ->
                        navigationEntryProvider(key, navigationState)
                    }
                )
            }
        }

        // When: 画面が表示される（アクションなし）

        // Then: ホーム画面のボタンが全て表示され、バックスタックにはホーム画面のみが存在する
        onNodeWithTag("start_button").assertIsDisplayed()
        onNodeWithTag("medal_collection_button").assertIsDisplayed()
        onNodeWithTag("settings_button").assertIsDisplayed()

        assertEquals(1, navigationState.entries.size)
        assertEquals(HomeRoute, navigationState.entries.first())
    }

    @Test
    fun ホーム画面でスタートボタンを押すとモード選択画面に遷移する() = runComposeUiTest {
        // Given: ホーム画面を初期画面として表示する
        val backStack = mutableStateListOf<SansuuKidsRoute>(HomeRoute)
        val navigationState = NavigationState(backStack)

        setContent {
            SansuuKidsTheme {
                NavDisplay(
                    backStack = navigationState.entries,
                    onBack = { navigationState.navigateBack() },
                    entryProvider = { key ->
                        navigationEntryProvider(key, navigationState)
                    }
                )
            }
        }

        // When: スタートボタンをクリックする
        onNodeWithTag("start_button").performClick()

        // Then: モード選択画面に遷移する
        assertEquals(2, navigationState.entries.size)
        assertEquals(ModeSelectionRoute, navigationState.entries.last())
        onNodeWithTag("addition_button").assertIsDisplayed()
    }

    @Test
    fun ホーム画面で戻るとバックスタックが空になる() = runComposeUiTest {
        // Given: ホーム画面のみがバックスタックに存在する
        val backStack = mutableStateListOf<SansuuKidsRoute>(HomeRoute)
        val navigationState = NavigationState(backStack)

        setContent {
            SansuuKidsTheme {
                NavDisplay(
                    backStack = navigationState.entries,
                    onBack = { navigationState.navigateBack() },
                    entryProvider = { key ->
                        navigationEntryProvider(key, navigationState)
                    }
                )
            }
        }

        // When: 戻るナビゲーションを実行する
        navigationState.navigateBack()

        // Then: バックスタックが空になる（実際の挙動はnavigation3の責務）
        assertEquals(0, navigationState.entries.size)
    }

    @Test
    fun モード選択画面で戻ると前のホーム画面に戻る() = runComposeUiTest {
        // Given: モード選択画面を表示する
        val backStack = mutableStateListOf(HomeRoute, ModeSelectionRoute)
        val navigationState = NavigationState(backStack)

        setContent {
            SansuuKidsTheme {
                NavDisplay(
                    backStack = navigationState.entries,
                    onBack = { navigationState.navigateBack() },
                    entryProvider = { key ->
                        navigationEntryProvider(key, navigationState)
                    }
                )
            }
        }

        // When: 戻るナビゲーションを実行する
        navigationState.navigateBack()
        waitForIdle()

        // Then: ホーム画面に戻る
        assertEquals(1, navigationState.entries.size)
        assertEquals(HomeRoute, navigationState.entries.last())
        onNodeWithTag("start_button").assertIsDisplayed()
    }

    @Test
    fun 往復のナビゲーションが正しく動作する() = runComposeUiTest {
        // Given: ホーム画面を初期画面として表示する
        val backStack = mutableStateListOf<SansuuKidsRoute>(HomeRoute)
        val navigationState = NavigationState(backStack)

        setContent {
            SansuuKidsTheme {
                NavDisplay(
                    backStack = navigationState.entries,
                    onBack = { navigationState.navigateBack() },
                    entryProvider = { key ->
                        navigationEntryProvider(key, navigationState)
                    }
                )
            }
        }

        // When: スタートボタンをクリックして遷移し、戻り、再度遷移する
        onNodeWithTag("start_button").performClick()
        navigationState.navigateBack()
        waitForIdle()
        onNodeWithTag("start_button").performClick()

        // Then: モード選択画面に再度遷移できる
        onNodeWithTag("addition_button").assertIsDisplayed()
        assertEquals(2, navigationState.entries.size)
    }
}
