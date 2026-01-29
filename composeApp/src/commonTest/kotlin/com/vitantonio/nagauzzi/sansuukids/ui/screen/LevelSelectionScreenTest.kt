package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class LevelSelectionScreenTest {

    @Test
    fun かんたんボタンを押すとonEasyClickが呼ばれる() = runComposeUiTest {
        // Given: レベル選択画面を表示し、クリック状態を追跡する
        var clicked = false
        setContent {
            SansuuKidsTheme {
                LevelSelectionScreen(
                    onEasyClick = { clicked = true },
                    onNormalClick = {},
                    onDifficultClick = {},
                    onBackClick = {}
                )
            }
        }

        // When: かんたんボタンをクリックする
        onNodeWithTag("easy_button").performClick()

        // Then: onEasyClickが呼ばれる
        assertTrue(clicked)
    }

    @Test
    fun ふつうボタンを押すとonNormalClickが呼ばれる() = runComposeUiTest {
        // Given: レベル選択画面を表示し、クリック状態を追跡する
        var clicked = false
        setContent {
            SansuuKidsTheme {
                LevelSelectionScreen(
                    onEasyClick = {},
                    onNormalClick = { clicked = true },
                    onDifficultClick = {},
                    onBackClick = {}
                )
            }
        }

        // When: ふつうボタンをクリックする
        onNodeWithTag("normal_button").performClick()

        // Then: onNormalClickが呼ばれる
        assertTrue(clicked)
    }

    @Test
    fun むずかしいボタンを押すとonDifficultClickが呼ばれる() = runComposeUiTest {
        // Given: レベル選択画面を表示し、クリック状態を追跡する
        var clicked = false
        setContent {
            SansuuKidsTheme {
                LevelSelectionScreen(
                    onEasyClick = {},
                    onNormalClick = {},
                    onDifficultClick = { clicked = true },
                    onBackClick = {}
                )
            }
        }

        // When: むずかしいボタンをクリックする
        onNodeWithTag("difficult_button").performClick()

        // Then: onDifficultClickが呼ばれる
        assertTrue(clicked)
    }
}
