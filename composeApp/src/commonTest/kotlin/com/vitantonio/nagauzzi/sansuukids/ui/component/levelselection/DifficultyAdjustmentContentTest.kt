package com.vitantonio.nagauzzi.sansuukids.ui.component.levelselection

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
internal class DifficultyAdjustmentContentTest {

    @Test
    fun 初期表示時にonQuizRangeChangedが呼ばれない() = runComposeUiTest {
        // Given: コールバックの呼び出し回数を追跡する
        var callCount = 0

        // When: 難易度調整コンテンツを表示する
        setContent {
            SansuuKidsTheme {
                DifficultyAdjustmentContent(
                    quizRange = QuizRange.Default(OperationType.Addition, Level.Normal),
                    onQuizRangeChanged = { callCount++ }
                )
            }
        }
        waitForIdle()

        // Then: 初期表示ではonQuizRangeChangedが呼ばれない（不要な永続化が発生しない）
        assertEquals(0, callCount)
    }

    @Test
    fun 外部からの出題範囲変更だけではonQuizRangeChangedが呼ばれない() = runComposeUiTest {
        // Given: 難易度調整コンテンツを表示する
        var callCount = 0
        var quizRange: QuizRange by mutableStateOf(
            QuizRange.Custom(OperationType.Addition, Level.Normal, min = 1, max = 99)
        )
        setContent {
            SansuuKidsTheme {
                DifficultyAdjustmentContent(
                    quizRange = quizRange,
                    onQuizRangeChanged = { callCount++ }
                )
            }
        }

        // When: 外部から出題範囲が複数回変更される（リセットやDataStore更新をシミュレート）
        quizRange = QuizRange.Default(OperationType.Addition, Level.Normal)
        waitForIdle()
        quizRange = QuizRange.Custom(OperationType.Addition, Level.Normal, min = 22, max = 88)
        waitForIdle()

        // Then: onQuizRangeChangedが呼ばれない（外部変更は永続化の対象外）
        assertEquals(0, callCount)
    }

    @Test
    fun 外部からの出題範囲リセットでメダル無効警告が消える() = runComposeUiTest {
        // Given: 最小値をデフォルトより小さく設定してメダル無効警告を表示する
        var quizRange: QuizRange by mutableStateOf(
            QuizRange.Custom(OperationType.Addition, Level.Normal, min = 1, max = 99)
        )
        setContent {
            SansuuKidsTheme {
                DifficultyAdjustmentContent(
                    quizRange = quizRange,
                    onQuizRangeChanged = {}
                )
            }
        }
        onNodeWithTag("difficulty_warning_addition").assertIsDisplayed()

        // When: 外部から出題範囲がデフォルト値にリセットされる
        quizRange = QuizRange.Default(OperationType.Addition, Level.Normal)
        waitForIdle()

        // Then: メダル無効警告が消える
        onNodeWithTag("difficulty_warning_addition").assertDoesNotExist()
    }

    @Test
    fun 外部からの出題範囲変更でラベルが更新される() = runComposeUiTest {
        // Given: カスタム範囲で表示する
        var quizRange: QuizRange by mutableStateOf(
            QuizRange.Custom(OperationType.Addition, Level.Normal, min = 1, max = 50)
        )
        setContent {
            SansuuKidsTheme {
                DifficultyAdjustmentContent(
                    quizRange = quizRange,
                    onQuizRangeChanged = {}
                )
            }
        }
        onNodeWithTag("difficulty_label_addition", useUnmergedTree = true)
            .assertTextContains("1", substring = true)
            .assertTextContains("50", substring = true)

        // When: 外部から出題範囲が変更される
        quizRange = QuizRange.Custom(OperationType.Addition, Level.Normal, min = 22, max = 88)
        waitForIdle()

        // Then: ラベルが新しい範囲を表示する
        onNodeWithTag("difficulty_label_addition", useUnmergedTree = true)
            .assertTextContains("22", substring = true)
            .assertTextContains("88", substring = true)
    }
}
