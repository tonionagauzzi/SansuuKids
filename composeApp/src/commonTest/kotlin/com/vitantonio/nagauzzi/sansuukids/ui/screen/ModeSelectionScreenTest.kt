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

        onNodeWithTag("addition_button").performClick()
        assertTrue(clicked)
    }

    @Test
    fun ひきざんボタンを押すとonSubtractionClickが呼ばれる() = runComposeUiTest {
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

        onNodeWithTag("subtraction_button").performClick()
        assertTrue(clicked)
    }

    @Test
    fun かけざんボタンを押すとonMultiplicationClickが呼ばれる() = runComposeUiTest {
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

        onNodeWithTag("multiplication_button").performClick()
        assertTrue(clicked)
    }

    @Test
    fun わりざんボタンを押すとonDivisionClickが呼ばれる() = runComposeUiTest {
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

        onNodeWithTag("division_button").performClick()
        assertTrue(clicked)
    }

    @Test
    fun すべてボタンを押すとonAllClickが呼ばれる() = runComposeUiTest {
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

        onNodeWithTag("all_button").performClick()
        assertTrue(clicked)
    }
}
