package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.vitantonio.nagauzzi.sansuukids.model.Question
import com.vitantonio.nagauzzi.sansuukids.model.UserAnswer
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class AnswerCheckScreenTest {

    @Test
    fun 正解の問題が正しく表示される() = runComposeUiTest {
        // Given: 正解の回答で答え合わせ画面を表示
        val questions = listOf(Question.Math.Addition(3, 5))
        val userAnswers = listOf(UserAnswer(0, 8, true))

        setContent {
            SansuuKidsTheme {
                AnswerCheckScreen(
                    questions = questions,
                    userAnswers = userAnswers,
                    onBackClick = {},
                    onFinishClick = {}
                )
            }
        }

        // Then: 正解であることが表示され、問題と正解が表示される
        onNodeWithTag("result_indicator").assertIsDisplayed()
        onNodeWithTag("question_text").assertTextContains("3 + 5 = 8", substring = true)
        onNodeWithTag("user_answer_display").assertIsDisplayed()
    }

    @Test
    fun 不正解の問題が正しく表示される() = runComposeUiTest {
        // Given: 不正解の回答で答え合わせ画面を表示
        val questions = listOf(Question.Math.Addition(3, 5))
        val userAnswers = listOf(UserAnswer(0, 7, false))

        setContent {
            SansuuKidsTheme {
                AnswerCheckScreen(
                    questions = questions,
                    userAnswers = userAnswers,
                    onBackClick = {},
                    onFinishClick = {}
                )
            }
        }

        // Then: 不正解であることが表示され、正解と自分の回答が表示される
        onNodeWithTag("result_indicator").assertIsDisplayed()
        onNodeWithTag("question_text").assertTextContains("3 + 5 = 8", substring = true)
        onNodeWithTag("user_answer_display").assertIsDisplayed()
    }

    @Test
    fun 最初の問題ではもどるボタンが表示される() = runComposeUiTest {
        // Given: 複数の問題があり、最初の問題を表示
        val questions = listOf(
            Question.Math.Addition(1, 2),
            Question.Math.Addition(3, 4)
        )
        val userAnswers = listOf(
            UserAnswer(0, 3, true),
            UserAnswer(1, 7, true)
        )

        setContent {
            SansuuKidsTheme {
                AnswerCheckScreen(
                    questions = questions,
                    userAnswers = userAnswers,
                    onBackClick = {},
                    onFinishClick = {}
                )
            }
        }

        // Then: もどるボタンが表示される（前ボタンは表示されない）
        onNodeWithTag("back_button").assertIsDisplayed()
        onNodeWithTag("previous_button").assertDoesNotExist()
        // 次ボタンは表示される
        onNodeWithTag("next_button").assertIsDisplayed()
    }

    @Test
    fun もどるボタンを押すとonFinishClickが呼ばれる() = runComposeUiTest {
        // Given: 複数の問題があり、最初の問題を表示
        var clicked = false
        val questions = listOf(
            Question.Math.Addition(1, 2),
            Question.Math.Addition(3, 4)
        )
        val userAnswers = listOf(
            UserAnswer(0, 3, true),
            UserAnswer(1, 7, true)
        )

        setContent {
            SansuuKidsTheme {
                AnswerCheckScreen(
                    questions = questions,
                    userAnswers = userAnswers,
                    onBackClick = { clicked = true },
                    onFinishClick = {}
                )
            }
        }

        // When: もどるボタンをクリックする
        onNodeWithTag("back_button").performClick()

        // Then: onFinishClickが呼ばれる
        assertTrue(clicked)
    }

    @Test
    fun 次ボタンを押すと次の問題に移動する() = runComposeUiTest {
        // Given: 複数の問題がある
        val questions = listOf(
            Question.Math.Addition(1, 2),
            Question.Math.Addition(3, 4)
        )
        val userAnswers = listOf(
            UserAnswer(0, 3, true),
            UserAnswer(1, 7, true)
        )

        setContent {
            SansuuKidsTheme {
                AnswerCheckScreen(
                    questions = questions,
                    userAnswers = userAnswers,
                    onBackClick = {},
                    onFinishClick = {}
                )
            }
        }

        // When: 次ボタンをクリックする
        onNodeWithTag("next_button").performClick()

        // Then: 2問目が表示される
        onNodeWithTag("question_text").assertTextContains("3 + 4 = 7", substring = true)
        // 前ボタンが表示される
        onNodeWithTag("previous_button").assertIsDisplayed()
    }

    @Test
    fun 前ボタンを押すと前の問題に戻る() = runComposeUiTest {
        // Given: 複数の問題があり、2問目を表示
        val questions = listOf(
            Question.Math.Addition(1, 2),
            Question.Math.Addition(3, 4)
        )
        val userAnswers = listOf(
            UserAnswer(0, 3, true),
            UserAnswer(1, 7, true)
        )

        setContent {
            SansuuKidsTheme {
                AnswerCheckScreen(
                    questions = questions,
                    userAnswers = userAnswers,
                    onBackClick = {},
                    onFinishClick = {}
                )
            }
        }

        // When: 次ボタンで2問目に移動し、前ボタンで1問目に戻る
        onNodeWithTag("next_button").performClick()
        onNodeWithTag("previous_button").performClick()

        // Then: 1問目が表示され、もどるボタンが表示される
        onNodeWithTag("question_text").assertTextContains("1 + 2 = 3", substring = true)
        onNodeWithTag("back_button").assertIsDisplayed()
    }

    @Test
    fun 最後の問題では終わるボタンが表示される() = runComposeUiTest {
        // Given: 1問だけの答え合わせ画面
        val questions = listOf(Question.Math.Addition(3, 5))
        val userAnswers = listOf(UserAnswer(0, 8, true))

        setContent {
            SansuuKidsTheme {
                AnswerCheckScreen(
                    questions = questions,
                    userAnswers = userAnswers,
                    onBackClick = {},
                    onFinishClick = {}
                )
            }
        }

        // Then: 終わるボタンが表示される
        onNodeWithTag("finish_button").assertIsDisplayed()
    }

    @Test
    fun 終わるボタンを押すとonFinishClickが呼ばれる() = runComposeUiTest {
        // Given: 1問の答え合わせ画面でクリック状態を追跡
        var clicked = false
        val questions = listOf(Question.Math.Addition(3, 5))
        val userAnswers = listOf(UserAnswer(0, 8, true))

        setContent {
            SansuuKidsTheme {
                AnswerCheckScreen(
                    questions = questions,
                    userAnswers = userAnswers,
                    onBackClick = {},
                    onFinishClick = { clicked = true }
                )
            }
        }

        // When: 終わるボタンをクリックする
        onNodeWithTag("finish_button").performClick()

        // Then: onFinishClickが呼ばれる
        assertTrue(clicked)
    }
}
