package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.model.MedalCount
import com.vitantonio.nagauzzi.sansuukids.model.MedalDisplay
import com.vitantonio.nagauzzi.sansuukids.model.Mode
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
        onNodeWithTag("back_button").performClick()

        // Then: onBackClickが呼ばれる
        assertTrue(clicked)
    }

    @Test
    fun メダルセルをタップするとダイアログが表示される() = runComposeUiTest {
        // Given: メダルを獲得済みの図鑑画面を表示する
        setContent {
            SansuuKidsTheme {
                MedalCollectionScreen(
                    medalDisplays = listOf(
                        MedalDisplay(
                            Mode.ADDITION,
                            Level.EASY,
                            Medal.Gold,
                            MedalCount(gold = 2, silver = 1)
                        )
                    ),
                    onBackClick = {}
                )
            }
        }

        // When: メダルセルをタップする
        onNodeWithTag("grid_cell_addition_easy").performClick()

        // Then: メダル内訳ダイアログが表示される
        onNodeWithTag("medal_detail_dialog", useUnmergedTree = true).assertIsDisplayed()
        onNodeWithTag("medal_detail_title", useUnmergedTree = true).assertIsDisplayed()
        onNodeWithTag("medal_count_gold", useUnmergedTree = true).assertIsDisplayed()
        onNodeWithTag("medal_count_silver", useUnmergedTree = true).assertIsDisplayed()
        onNodeWithTag("medal_count_bronze", useUnmergedTree = true).assertIsDisplayed()
        onNodeWithTag("medal_count_star", useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun 未獲得セルをタップしてもダイアログが表示されない() = runComposeUiTest {
        // Given: メダル未獲得の図鑑画面を表示する
        setContent {
            SansuuKidsTheme {
                MedalCollectionScreen(
                    medalDisplays = emptyList(),
                    onBackClick = {}
                )
            }
        }

        // When: 未獲得のメダルセルをタップする
        onNodeWithTag("grid_cell_addition_easy").performClick()

        // Then: ダイアログが表示されない
        onNodeWithTag("medal_detail_dialog").assertDoesNotExist()
    }
}
