package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class ModeSelectionScreenTest {

    @Test
    fun たしざんボタンを押すとonAdditionClickが呼ばれる() = runComposeUiTest {
        // Given: モード選択画面を表示し、クリック状態を追跡する
        var clicked = false
        setContent {
            SansuuKidsTheme {
                ModeSelectionScreen(
                    onAdditionClick = { clicked = true },
                    onSubtractionClick = {},
                    onMultiplicationClick = {},
                    onDivisionClick = {},
                    onAllClick = {}
                )
            }
        }

        // When: たしざんボタンをクリックする
        onNodeWithTag("addition_button").performClick()

        // Then: onAdditionClickが呼ばれる
        assertTrue(clicked)
    }

    @Test
    fun ひきざんボタンを押すとonSubtractionClickが呼ばれる() = runComposeUiTest {
        // Given: モード選択画面を表示し、クリック状態を追跡する
        var clicked = false
        setContent {
            SansuuKidsTheme {
                ModeSelectionScreen(
                    onAdditionClick = {},
                    onSubtractionClick = { clicked = true },
                    onMultiplicationClick = {},
                    onDivisionClick = {},
                    onAllClick = {}
                )
            }
        }

        // When: ひきざんボタンをクリックする
        onNodeWithTag("subtraction_button").performClick()

        // Then: onSubtractionClickが呼ばれる
        assertTrue(clicked)
    }

    @Test
    fun かけざんボタンを押すとonMultiplicationClickが呼ばれる() = runComposeUiTest {
        // Given: モード選択画面を表示し、クリック状態を追跡する
        var clicked = false
        setContent {
            SansuuKidsTheme {
                ModeSelectionScreen(
                    onAdditionClick = {},
                    onSubtractionClick = {},
                    onMultiplicationClick = { clicked = true },
                    onDivisionClick = {},
                    onAllClick = {}
                )
            }
        }

        // When: かけざんボタンをクリックする
        onNodeWithTag("multiplication_button").performClick()

        // Then: onMultiplicationClickが呼ばれる
        assertTrue(clicked)
    }

    @Test
    fun わりざんボタンを押すとonDivisionClickが呼ばれる() = runComposeUiTest {
        // Given: モード選択画面を表示し、クリック状態を追跡する
        var clicked = false
        setContent {
            SansuuKidsTheme {
                ModeSelectionScreen(
                    onAdditionClick = {},
                    onSubtractionClick = {},
                    onMultiplicationClick = {},
                    onDivisionClick = { clicked = true },
                    onAllClick = {}
                )
            }
        }

        // When: わりざんボタンをクリックする
        onNodeWithTag("division_button").performClick()

        // Then: onDivisionClickが呼ばれる
        assertTrue(clicked)
    }

    @Test
    fun すべてボタンを押すとonAllClickが呼ばれる() = runComposeUiTest {
        // Given: モード選択画面を表示し、クリック状態を追跡する
        var clicked = false
        setContent {
            SansuuKidsTheme {
                ModeSelectionScreen(
                    onAdditionClick = {},
                    onSubtractionClick = {},
                    onMultiplicationClick = {},
                    onDivisionClick = {},
                    onAllClick = { clicked = true }
                )
            }
        }

        // When: すべてボタンをクリックする
        onNodeWithTag("all_button").performClick()

        // Then: onAllClickが呼ばれる
        assertTrue(clicked)
    }
}
