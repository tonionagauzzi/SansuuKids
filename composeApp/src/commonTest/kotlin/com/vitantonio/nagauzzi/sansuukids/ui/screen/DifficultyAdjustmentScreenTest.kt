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

    @Test
    fun デフォルトの出題範囲ではメダル無効警告が表示されない() = runComposeUiTest {
        // Given: デフォルトの出題範囲で難易度調整画面を表示する
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

        // Then: メダル無効警告が表示されない
        onNodeWithTag("difficulty_warning_addition").assertDoesNotExist()
    }

    @Test
    fun 最小値をデフォルトより小さくするとメダル無効警告が表示される() = runComposeUiTest {
        // Given: 最小値をデフォルト(11)より小さく設定した難易度調整画面を表示する
        setContent {
            SansuuKidsTheme {
                DifficultyAdjustmentScreen(
                    level = Level.Normal,
                    operationType = OperationType.Addition,
                    quizRange = QuizRange.Custom(
                        operationType = OperationType.Addition,
                        level = Level.Normal,
                        min = 1,
                        max = 99
                    ),
                    onRangeChanged = { _, _, _ -> },
                    onReset = {},
                    onBackClick = {}
                )
            }
        }

        // When: 画面が表示される

        // Then: メダル無効警告が表示される
        onNodeWithTag("difficulty_warning_addition").assertIsDisplayed()
    }

    @Test
    fun 最大値をデフォルトより小さくするとメダル無効警告が表示される() = runComposeUiTest {
        // Given: 最大値をデフォルト(99)より小さく設定した難易度調整画面を表示する
        setContent {
            SansuuKidsTheme {
                DifficultyAdjustmentScreen(
                    level = Level.Normal,
                    operationType = OperationType.Addition,
                    quizRange = QuizRange.Custom(
                        operationType = OperationType.Addition,
                        level = Level.Normal,
                        min = 11,
                        max = 50
                    ),
                    onRangeChanged = { _, _, _ -> },
                    onReset = {},
                    onBackClick = {}
                )
            }
        }

        // When: 画面が表示される

        // Then: メダル無効警告が表示される
        onNodeWithTag("difficulty_warning_addition").assertIsDisplayed()
    }

    @Test
    fun 最小値と最大値の両方をデフォルトより小さくするとメダル無効警告が表示される() = runComposeUiTest {
        // Given: 最小値と最大値の両方をデフォルト(min=11, max=99)より小さく設定した難易度調整画面を表示する
        setContent {
            SansuuKidsTheme {
                DifficultyAdjustmentScreen(
                    level = Level.Normal,
                    operationType = OperationType.Addition,
                    quizRange = QuizRange.Custom(
                        operationType = OperationType.Addition,
                        level = Level.Normal,
                        min = 1,
                        max = 50
                    ),
                    onRangeChanged = { _, _, _ -> },
                    onReset = {},
                    onBackClick = {}
                )
            }
        }

        // When: 画面が表示される

        // Then: メダル無効警告が表示される
        onNodeWithTag("difficulty_warning_addition").assertIsDisplayed()
    }
}
