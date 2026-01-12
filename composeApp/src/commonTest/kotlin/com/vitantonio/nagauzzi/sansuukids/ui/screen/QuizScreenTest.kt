package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.model.Question
import com.vitantonio.nagauzzi.sansuukids.model.Quiz
import com.vitantonio.nagauzzi.sansuukids.model.QuizState
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class QuizScreenTest {
    private val quizSize = 3

    private fun createTestQuiz(): Quiz {
        val questions = (1..quizSize).map { index ->
            Question.Addition(
                leftOperand = index,
                rightOperand = 1
            )
        }
        return Quiz(questions, Mode.ADDITION, Level.EASY)
    }

    @Test
    fun テンキーの数字ボタンをタップするとonDigitClickが呼ばれる() = runComposeUiTest {
        // Given: クイズ画面を表示し、クリック状態を追跡する
        var clickedDigit: Int? = null
        val quizState = QuizState(createTestQuiz())
        setContent {
            SansuuKidsTheme {
                QuizScreen(
                    quizState = quizState,
                    onDigitClick = { digit -> clickedDigit = digit },
                    onDeleteClick = {},
                    onSubmitClick = {},
                    onCancelClick = {}
                )
            }
        }

        // When: 数字ボタン5をクリックする
        onNodeWithTag("keypad_5").performClick()

        // Then: onDigitClickが5で呼ばれる
        assertEquals(5, clickedDigit)
    }

    @Test
    fun 削除ボタンをタップするとonDeleteClickが呼ばれる() = runComposeUiTest {
        // Given: クイズ画面を表示し、クリック状態を追跡する
        var clicked = false
        val quizState = QuizState(createTestQuiz())
        setContent {
            SansuuKidsTheme {
                QuizScreen(
                    quizState = quizState,
                    onDigitClick = {},
                    onDeleteClick = { clicked = true },
                    onSubmitClick = {},
                    onCancelClick = {}
                )
            }
        }

        // When: 削除ボタンをクリックする
        onNodeWithTag("keypad_delete").performClick()

        // Then: onDeleteClickが呼ばれる
        assertTrue(clicked)
    }

    @Test
    fun 中止ボタンをタップするとonCancelClickが呼ばれる() = runComposeUiTest {
        // Given: クイズ画面を表示し、クリック状態を追跡する
        var clicked = false
        val quizState = QuizState(createTestQuiz())
        setContent {
            SansuuKidsTheme {
                QuizScreen(
                    quizState = quizState,
                    onDigitClick = {},
                    onDeleteClick = {},
                    onSubmitClick = {},
                    onCancelClick = { clicked = true }
                )
            }
        }

        // When: 中止ボタンをクリックする
        onNodeWithTag("cancel_button").performClick()

        // Then: onCancelClickが呼ばれる
        assertTrue(clicked)
    }

    @Test
    fun 問題文が正しく表示される() = runComposeUiTest {
        // Given: テスト用クイズを作成する（最初の問題は1+1=?）
        val quizState = QuizState(createTestQuiz())
        setContent {
            SansuuKidsTheme {
                QuizScreen(
                    quizState = quizState,
                    onDigitClick = {},
                    onDeleteClick = {},
                    onSubmitClick = {},
                    onCancelClick = {}
                )
            }
        }

        // When: 画面が表示される

        // Then: 最初の問題が表示される
        onNodeWithTag("question_text").assertTextEquals("1 + 1 = ?")
    }

    @Test
    fun 入力がない場合は決定ボタンが無効化される() = runComposeUiTest {
        // Given: 入力なしのクイズ画面を表示する
        val quizState = QuizState(createTestQuiz())
        setContent {
            SansuuKidsTheme {
                QuizScreen(
                    quizState = quizState,
                    onDigitClick = {},
                    onDeleteClick = {},
                    onSubmitClick = {},
                    onCancelClick = {}
                )
            }
        }

        // When: 数字を入力しない

        // Then: 決定ボタンが無効化されている
        onNodeWithTag("keypad_submit").assertIsNotEnabled()
    }

    @Test
    fun 入力がある場合は決定ボタンが有効化される() = runComposeUiTest {
        // Given: 数字が入力済みのクイズ画面を表示する
        val quizState = QuizState(
            quiz = createTestQuiz(),
            currentInput = "5"
        )
        setContent {
            SansuuKidsTheme {
                QuizScreen(
                    quizState = quizState,
                    onDigitClick = {},
                    onDeleteClick = {},
                    onSubmitClick = {},
                    onCancelClick = {}
                )
            }
        }

        // When: 画面が表示される（入力済みの状態）

        // Then: 決定ボタンが有効化されている
        onNodeWithTag("keypad_submit").assertIsEnabled()
    }
}
