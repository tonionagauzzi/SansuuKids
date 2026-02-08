package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Addition
import com.vitantonio.nagauzzi.sansuukids.model.Quiz
import com.vitantonio.nagauzzi.sansuukids.model.QuizState
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class QuizScreenTest {
    private val quizSize = 3

    private fun createTestQuiz(level: Level = Level.Easy): Quiz {
        val questions = (1..quizSize).map { index ->
            Addition(
                leftOperand = index,
                rightOperand = 1
            )
        }
        return Quiz(questions, Mode.Addition, level)
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
                    perQuestionAnswerCheckEnabled = false,
                    hintDisplayEnabled = false,
                    onDigitClick = { digit -> clickedDigit = digit },
                    onDeleteClick = {},
                    onSubmitClick = {},
                    onBackClick = {}
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
                    perQuestionAnswerCheckEnabled = false,
                    hintDisplayEnabled = false,
                    onDigitClick = {},
                    onDeleteClick = { clicked = true },
                    onSubmitClick = {},
                    onBackClick = {}
                )
            }
        }

        // When: 削除ボタンをクリックする
        onNodeWithTag("keypad_delete").performClick()

        // Then: onDeleteClickが呼ばれる
        assertTrue(clicked)
    }

    @Test
    fun 中止ボタンをタップするとonBackClickが呼ばれる() = runComposeUiTest {
        // Given: クイズ画面を表示し、クリック状態を追跡する
        var clicked = false
        val quizState = QuizState(createTestQuiz())
        setContent {
            SansuuKidsTheme {
                QuizScreen(
                    quizState = quizState,
                    perQuestionAnswerCheckEnabled = false,
                    hintDisplayEnabled = false,
                    onDigitClick = {},
                    onDeleteClick = {},
                    onSubmitClick = {},
                    onBackClick = { clicked = true }
                )
            }
        }

        // When: 中止ボタンをクリックする
        onNodeWithTag("cancel_button").performClick()

        // Then: onBackClickが呼ばれる
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
                    perQuestionAnswerCheckEnabled = false,
                    hintDisplayEnabled = false,
                    onDigitClick = {},
                    onDeleteClick = {},
                    onSubmitClick = {},
                    onBackClick = {}
                )
            }
        }

        // When: 画面が表示される

        // Then: 最初の問題が表示される（末尾スペースなし）
        onNodeWithTag("question_text").assertTextEquals("1 + 1 =")
    }

    @Test
    fun 入力がない場合は決定ボタンが無効化される() = runComposeUiTest {
        // Given: 入力なしのクイズ画面を表示する
        val quizState = QuizState(createTestQuiz())
        setContent {
            SansuuKidsTheme {
                QuizScreen(
                    quizState = quizState,
                    perQuestionAnswerCheckEnabled = false,
                    hintDisplayEnabled = false,
                    onDigitClick = {},
                    onDeleteClick = {},
                    onSubmitClick = {},
                    onBackClick = {}
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
            currentInput = 5
        )
        setContent {
            SansuuKidsTheme {
                QuizScreen(
                    quizState = quizState,
                    perQuestionAnswerCheckEnabled = false,
                    hintDisplayEnabled = false,
                    onDigitClick = {},
                    onDeleteClick = {},
                    onSubmitClick = {},
                    onBackClick = {}
                )
            }
        }

        // When: 画面が表示される（入力済みの状態）

        // Then: 決定ボタンが有効化されている
        onNodeWithTag("keypad_submit").assertIsEnabled()
    }

    @Test
    fun 一問ごとの答え合わせが有効の場合に決定ボタン押下で答え合わせダイアログが表示される() =
        runComposeUiTest {
            // Given: 答え合わせ有効でクイズ画面を表示
            var submitCalled = false
            val quizState = QuizState(
                quiz = createTestQuiz(),
                currentInput = 5
            )

            setContent {
                SansuuKidsTheme {
                    QuizScreen(
                        quizState = quizState,
                        perQuestionAnswerCheckEnabled = true,
                        hintDisplayEnabled = false,
                        onDigitClick = {},
                        onDeleteClick = {},
                        onSubmitClick = { submitCalled = true },
                        onBackClick = {}
                    )
                }
            }

            // When: 決定ボタンをクリック
            onNodeWithTag("keypad_submit").performClick()
            waitForIdle()

            // Then: ダイアログ内の要素が表示され、まだsubmitは呼ばれていない
            onAllNodesWithTag("result_indicator", useUnmergedTree = true)[0].assertIsDisplayed()
            onAllNodesWithTag("question_text", useUnmergedTree = true)[0].assertIsDisplayed()
            onAllNodesWithTag("user_answer_display", useUnmergedTree = true)[0].assertIsDisplayed()
            assertFalse(submitCalled)
        }

    @Test
    fun 答え合わせダイアログの表示部分をクリックするとonSubmitClickが呼ばれる() = runComposeUiTest {
        // Given: 答え合わせダイアログ表示中
        var submitCalled = false
        val quizState = QuizState(
            quiz = createTestQuiz(),
            currentInput = 2
        )

        setContent {
            SansuuKidsTheme {
                QuizScreen(
                    quizState = quizState,
                    perQuestionAnswerCheckEnabled = true,
                    hintDisplayEnabled = false,
                    onDigitClick = {},
                    onDeleteClick = {},
                    onSubmitClick = { submitCalled = true },
                    onBackClick = {}
                )
            }
        }

        onNodeWithTag("keypad_submit").performClick()

        // When: ダイアログのCard部分をクリック
        // Note: ダイアログ自体はクリック可能なので、result_indicatorをクリックすることで間接的にCardをクリック
        onAllNodesWithTag("result_indicator", useUnmergedTree = true)[0].performClick()

        // Then: onSubmitClickが呼ばれる
        assertTrue(submitCalled)
    }

    @Test
    fun 一問ごとの答え合わせが無効の場合は即座にonSubmitClickが呼ばれる() = runComposeUiTest {
        // Given: 答え合わせ無効でクイズ画面を表示
        var submitCalled = false
        val quizState = QuizState(
            quiz = createTestQuiz(),
            currentInput = 5
        )

        setContent {
            SansuuKidsTheme {
                QuizScreen(
                    quizState = quizState,
                    perQuestionAnswerCheckEnabled = false,
                    hintDisplayEnabled = false,
                    onDigitClick = {},
                    onDeleteClick = {},
                    onSubmitClick = { submitCalled = true },
                    onBackClick = {}
                )
            }
        }

        // When: 決定ボタンをクリック
        onNodeWithTag("keypad_submit").performClick()

        // Then: ダイアログは表示されず、即座にonSubmitClickが呼ばれる
        onAllNodesWithTag("result_indicator", useUnmergedTree = true)[0].assertDoesNotExist()
        assertTrue(submitCalled)
    }

    @Test
    fun ヒント有効の場合ヒントエリアが表示される() = runComposeUiTest {
        // Given: かんたんモードでヒント有効のクイズ画面を表示
        val quizState = QuizState(createTestQuiz(Level.Easy))
        setContent {
            SansuuKidsTheme {
                QuizScreen(
                    quizState = quizState,
                    perQuestionAnswerCheckEnabled = false,
                    hintDisplayEnabled = true,
                    onDigitClick = {},
                    onDeleteClick = {},
                    onSubmitClick = {},
                    onBackClick = {}
                )
            }
        }

        // Then: ヒントエリアが表示される
        onNodeWithTag("hint_area").assertIsDisplayed()
    }

    @Test
    fun ヒント無効の場合ヒントエリアが表示されない() = runComposeUiTest {
        // Given: かんたんモードでヒント無効のクイズ画面を表示
        val quizState = QuizState(createTestQuiz(Level.Easy))
        setContent {
            SansuuKidsTheme {
                QuizScreen(
                    quizState = quizState,
                    perQuestionAnswerCheckEnabled = false,
                    hintDisplayEnabled = false,
                    onDigitClick = {},
                    onDeleteClick = {},
                    onSubmitClick = {},
                    onBackClick = {}
                )
            }
        }

        // Then: ヒントエリアが表示されない
        onNodeWithTag("hint_area").assertDoesNotExist()
    }
}
