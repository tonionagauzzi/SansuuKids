package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.model.MedalDisplay
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class MedalCollectionScreenTest {

    @Test
    fun タイトルが表示される() = runComposeUiTest {
        // Given: メダル図鑑画面を表示する
        setContent {
            SansuuKidsTheme {
                MedalCollectionScreen(
                    medalDisplays = emptyList(),
                    onBackClick = {}
                )
            }
        }

        // When: 画面が表示される（アクションなし）

        // Then: タイトルが表示される
        onNodeWithTag("medal_collection_title").assertIsDisplayed()
    }

    @Test
    fun メダル図鑑が正しく表示される() = runComposeUiTest {
        // Given: いくつかのメダルを持つメダル図鑑画面を表示する
        val medals = listOf(
            MedalDisplay(
                mode = Mode.ADDITION,
                level = Level.EASY,
                medal = Medal.Gold
            ),
            MedalDisplay(
                mode = Mode.SUBTRACTION,
                level = Level.NORMAL,
                medal = Medal.Silver
            ),
            MedalDisplay(
                mode = Mode.ALL,
                level = Level.DIFFICULT,
                medal = Medal.Bronze
            )
        )
        setContent {
            SansuuKidsTheme {
                MedalCollectionScreen(
                    medalDisplays = medals,
                    onBackClick = {}
                )
            }
        }

        // When: 画面が表示される（アクションなし）

        // Then: 指定したセルが表示される（セルの内容はUI上で確認）
        onNodeWithTag("grid_cell_addition_easy").assertIsDisplayed() // Addition, Easy - Gold
        onNodeWithTag("grid_cell_subtraction_normal").assertIsDisplayed() // Subtraction, Normal - Silver
        onNodeWithTag("grid_cell_all_difficult").assertIsDisplayed() // All, Difficult - Bronze
    }

    @Test
    fun 戻るボタンが表示される() = runComposeUiTest {
        // Given: メダル図鑑画面を表示する
        setContent {
            SansuuKidsTheme {
                MedalCollectionScreen(
                    medalDisplays = emptyList(),
                    onBackClick = {}
                )
            }
        }

        // When: 画面が表示される（アクションなし）

        // Then: 戻るボタンが表示される
        onNodeWithTag("medal_collection_back_button").assertIsDisplayed()
    }

    @Test
    fun 戻るボタンを押すとonBackClickが呼ばれる() = runComposeUiTest {
        // Given: メダル図鑑画面を表示し、クリック状態を追跡する
        var clicked = false
        setContent {
            SansuuKidsTheme {
                MedalCollectionScreen(
                    medalDisplays = emptyList(),
                    onBackClick = { clicked = true }
                )
            }
        }

        // When: 戻るボタンをクリックする
        onNodeWithTag("medal_collection_back_button").performClick()

        // Then: onBackClickが呼ばれる
        assertTrue(clicked)
    }
}
