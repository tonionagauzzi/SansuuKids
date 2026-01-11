package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class HomeScreenTest {

    @Test
    fun スタートボタンを押すとonStartClickが呼ばれる() = runComposeUiTest {
        // Given: ホーム画面を表示し、クリック状態を追跡する
        var clicked = false
        setContent {
            SansuuKidsTheme {
                HomeScreen(
                    onStartClick = { clicked = true },
                    onMedalCollectionClick = {},
                    onSettingsClick = {}
                )
            }
        }

        // When: スタートボタンをクリックする
        onNodeWithTag("start_button").performClick()

        // Then: onStartClickが呼ばれる
        assertTrue(clicked)
    }

    @Test
    fun メダルずかんボタンを押すとonMedalCollectionClickが呼ばれる() = runComposeUiTest {
        // Given: ホーム画面を表示し、クリック状態を追跡する
        var clicked = false
        setContent {
            SansuuKidsTheme {
                HomeScreen(
                    onStartClick = {},
                    onMedalCollectionClick = { clicked = true },
                    onSettingsClick = {}
                )
            }
        }

        // When: メダルずかんボタンをクリックする
        onNodeWithTag("medal_collection_button").performClick()

        // Then: onMedalCollectionClickが呼ばれる
        assertTrue(clicked)
    }

    @Test
    fun せっていボタンを押すとonSettingsClickが呼ばれる() = runComposeUiTest {
        // Given: ホーム画面を表示し、クリック状態を追跡する
        var clicked = false
        setContent {
            SansuuKidsTheme {
                HomeScreen(
                    onStartClick = {},
                    onMedalCollectionClick = {},
                    onSettingsClick = { clicked = true }
                )
            }
        }

        // When: せっていボタンをクリックする
        onNodeWithTag("settings_button").performClick()

        // Then: onSettingsClickが呼ばれる
        assertTrue(clicked)
    }
}
