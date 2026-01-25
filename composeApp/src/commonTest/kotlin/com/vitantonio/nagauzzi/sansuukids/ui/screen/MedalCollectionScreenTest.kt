package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class MedalCollectionScreenTest {

    @Test
    fun タイトルとメダル図鑑が正しく表示される() = runComposeUiTest {
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

        // Then: タイトルとメダル図鑑が表示される（セルは無作為に抽出）
        onNodeWithTag("medal_collection_title").assertIsDisplayed()
        onNodeWithTag("grid_cell_addition_easy").assertIsDisplayed() // Addition, Easy - Gold
        onNodeWithTag("grid_cell_subtraction_normal").assertIsDisplayed() // Subtraction, Normal - Silver
        onNodeWithTag("grid_cell_all_difficult").assertIsDisplayed() // All, Difficult - Bronze
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
