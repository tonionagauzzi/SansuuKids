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

        onNodeWithTag("start_button").performClick()
        assertTrue(clicked)
    }

    @Test
    fun メダルずかんボタンを押すとonMedalCollectionClickが呼ばれる() = runComposeUiTest {
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

        onNodeWithTag("medal_collection_button").performClick()
        assertTrue(clicked)
    }

    @Test
    fun せっていボタンを押すとonSettingsClickが呼ばれる() = runComposeUiTest {
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

        onNodeWithTag("settings_button").performClick()
        assertTrue(clicked)
    }
}
