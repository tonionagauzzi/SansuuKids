package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class LevelSelectionScreenTest {

    @Test
    fun かんたんボタンを押すとonClickにEasyが渡される() = runComposeUiTest {
        // Given: レベル選択画面を表示し、クリック状態を追跡する
        var clickedLevel: Level? = null
        setContent {
            SansuuKidsTheme {
                LevelSelectionScreen(
                    onClick = { clickedLevel = it },
                    onSettingClick = {},
                    onBackClick = {}
                )
            }
        }

        // When: かんたんボタンをクリックする
        onNodeWithTag("easy_button").performClick()

        // Then: onClickにEasyが渡される
        assertEquals(Level.Easy, clickedLevel)
    }

    @Test
    fun ふつうボタンを押すとonClickにNormalが渡される() = runComposeUiTest {
        // Given: レベル選択画面を表示し、クリック状態を追跡する
        var clickedLevel: Level? = null
        setContent {
            SansuuKidsTheme {
                LevelSelectionScreen(
                    onClick = { clickedLevel = it },
                    onSettingClick = {},
                    onBackClick = {}
                )
            }
        }

        // When: ふつうボタンをクリックする
        onNodeWithTag("normal_button").performClick()

        // Then: onClickにNormalが渡される
        assertEquals(Level.Normal, clickedLevel)
    }

    @Test
    fun むずかしいボタンを押すとonClickにDifficultが渡される() = runComposeUiTest {
        // Given: レベル選択画面を表示し、クリック状態を追跡する
        var clickedLevel: Level? = null
        setContent {
            SansuuKidsTheme {
                LevelSelectionScreen(
                    onClick = { clickedLevel = it },
                    onSettingClick = {},
                    onBackClick = {}
                )
            }
        }

        // When: むずかしいボタンをクリックする
        onNodeWithTag("difficult_button").performClick()

        // Then: onClickにDifficultが渡される
        assertEquals(Level.Difficult, clickedLevel)
    }
}
