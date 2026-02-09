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
                    isEnabledSetting = true,
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
    fun かんたんの設定アイコンを押すとonSettingClickにEasyが渡される() = runComposeUiTest {
        // Given: レベル選択画面を表示し、クリック状態を追跡する
        var clickedLevel: Level? = null
        setContent {
            SansuuKidsTheme {
                LevelSelectionScreen(
                    isEnabledSetting = true,
                    onClick = {},
                    onSettingClick = { clickedLevel = it },
                    onBackClick = {}
                )
            }
        }

        // When: かんたんの設定アイコンをクリックする
        onNodeWithTag("easy_setting_button").performClick()

        // Then: onSettingClickにEasyが渡される
        assertEquals(Level.Easy, clickedLevel)
    }

    @Test
    fun ふつうボタンを押すとonClickにNormalが渡される() = runComposeUiTest {
        // Given: レベル選択画面を表示し、クリック状態を追跡する
        var clickedLevel: Level? = null
        setContent {
            SansuuKidsTheme {
                LevelSelectionScreen(
                    isEnabledSetting = true,
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
    fun ふつうの設定アイコンを押すとonSettingClickにNormalが渡される() = runComposeUiTest {
        // Given: レベル選択画面を表示し、クリック状態を追跡する
        var clickedLevel: Level? = null
        setContent {
            SansuuKidsTheme {
                LevelSelectionScreen(
                    isEnabledSetting = true,
                    onClick = {},
                    onSettingClick = { clickedLevel = it },
                    onBackClick = {}
                )
            }
        }

        // When: ふつうの設定アイコンをクリックする
        onNodeWithTag("normal_setting_button").performClick()

        // Then: onSettingClickにNormalが渡される
        assertEquals(Level.Normal, clickedLevel)
    }

    @Test
    fun むずかしいボタンを押すとonClickにDifficultが渡される() = runComposeUiTest {
        // Given: レベル選択画面を表示し、クリック状態を追跡する
        var clickedLevel: Level? = null
        setContent {
            SansuuKidsTheme {
                LevelSelectionScreen(
                    isEnabledSetting = true,
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

    @Test
    fun むずかしい設定アイコンを押すとonSettingClickにDifficultが渡される() = runComposeUiTest {
        // Given: レベル選択画面を表示し、クリック状態を追跡する
        var clickedLevel: Level? = null
        setContent {
            SansuuKidsTheme {
                LevelSelectionScreen(
                    isEnabledSetting = true,
                    onClick = {},
                    onSettingClick = { clickedLevel = it },
                    onBackClick = {}
                )
            }
        }

        // When: むずかしい設定アイコンをクリックする
        onNodeWithTag("difficult_setting_button").performClick()

        // Then: onSettingClickにDifficultが渡される
        assertEquals(Level.Difficult, clickedLevel)
    }
}
