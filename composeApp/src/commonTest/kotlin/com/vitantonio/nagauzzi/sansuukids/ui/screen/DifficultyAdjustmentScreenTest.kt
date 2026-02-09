package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class DifficultyAdjustmentScreenTest {

    @Test
    fun ヘッダータイトルが表示される() = runComposeUiTest {
        // Given: 難易度調整画面を表示する
        setContent {
            SansuuKidsTheme {
                DifficultyAdjustmentScreen(
                    level = Level.Normal,
                    operationType = OperationType.Addition,
                    quizRange = QuizRange.Default(OperationType.Addition, Level.Normal),
                    onRangeChanged = { _, _, _ -> },
                    onReset = {},
                    onBackClick = {}
                )
            }
        }

        // When: 画面が表示される

        // Then: ヘッダータイトルが表示される
        onNodeWithTag("difficulty_adjustment_title").assertIsDisplayed()
    }

    @Test
    fun 戻るボタンを押すとonBackClickが呼ばれる() = runComposeUiTest {
        // Given: 難易度調整画面を表示し、クリック状態を追跡する
        var clicked = false
        setContent {
            SansuuKidsTheme {
                DifficultyAdjustmentScreen(
                    level = Level.Normal,
                    operationType = OperationType.Addition,
                    quizRange = QuizRange.Default(OperationType.Addition, Level.Normal),
                    onRangeChanged = { _, _, _ -> },
                    onReset = {},
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
    fun スライダーのラベルが表示される() = runComposeUiTest {
        // Given: たしざんの難易度調整画面を表示する
        setContent {
            SansuuKidsTheme {
                DifficultyAdjustmentScreen(
                    level = Level.Normal,
                    operationType = OperationType.Addition,
                    quizRange = QuizRange.Default(OperationType.Addition, Level.Normal),
                    onRangeChanged = { _, _, _ -> },
                    onReset = {},
                    onBackClick = {}
                )
            }
        }

        // When: 画面が表示される

        // Then: スライダーのラベルが表示される
        onNodeWithTag("difficulty_label_addition").assertIsDisplayed()
    }

    @Test
    fun リセットボタンが表示される() = runComposeUiTest {
        // Given: たしざんの難易度調整画面を表示する
        setContent {
            SansuuKidsTheme {
                DifficultyAdjustmentScreen(
                    level = Level.Normal,
                    operationType = OperationType.Addition,
                    quizRange = QuizRange.Default(OperationType.Addition, Level.Normal),
                    onRangeChanged = { _, _, _ -> },
                    onReset = {},
                    onBackClick = {}
                )
            }
        }

        // When: 画面が表示される

        // Then: リセットボタンが表示される
        onNodeWithTag("action_button").assertIsDisplayed()
    }
}
