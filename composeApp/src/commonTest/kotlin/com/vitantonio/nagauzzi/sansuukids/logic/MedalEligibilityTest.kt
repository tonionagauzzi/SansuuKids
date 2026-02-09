package com.vitantonio.nagauzzi.sansuukids.logic

import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MedalEligibilityTest {
    private val medalEligibility = MedalEligibility()

    @Test
    fun デフォルト範囲の場合はメダル獲得可能() {
        // Given: デフォルト範囲
        val quizRange = QuizRange.Default(OperationType.Addition, Level.Easy)

        // When: メダル適格を判定する
        val result = medalEligibility(OperationType.Addition, Level.Easy, quizRange)

        // Then: メダル獲得可能
        assertTrue(result)
    }

    @Test
    fun カスタムminがデフォルトmin以上の場合はメダル獲得可能() {
        // Given: カスタムminがデフォルトmin（1）以上
        val quizRange = QuizRange.Custom(OperationType.Addition, Level.Easy, min = 2, max = 5)

        // When: メダル適格を判定する
        val result = medalEligibility(OperationType.Addition, Level.Easy, quizRange)

        // Then: メダル獲得可能
        assertTrue(result)
    }

    @Test
    fun カスタムminがデフォルトminと同じ場合はメダル獲得可能() {
        // Given: カスタムminがデフォルトmin（1）と同じ
        val quizRange = QuizRange.Custom(OperationType.Addition, Level.Easy, min = 1, max = 10)

        // When: メダル適格を判定する
        val result = medalEligibility(OperationType.Addition, Level.Easy, quizRange)

        // Then: メダル獲得可能
        assertTrue(result)
    }

    @Test
    fun 引き算のカスタムminがデフォルトmin未満の場合はメダル獲得不可() {
        // Given: 引き算の普通レベルのデフォルトminは11、カスタムminを5に下げる
        val quizRange = QuizRange.Custom(OperationType.Subtraction, Level.Normal, min = 5, max = 99)

        // When: メダル適格を判定する
        val result = medalEligibility(OperationType.Subtraction, Level.Normal, quizRange)

        // Then: メダル獲得不可
        assertFalse(result)
    }

    @Test
    fun 掛け算のカスタムminがデフォルトmin未満の場合はメダル獲得不可() {
        // Given: 掛け算の普通レベルのデフォルトminは6、カスタムminを3に下げる
        val quizRange = QuizRange.Custom(OperationType.Multiplication, Level.Normal, min = 3, max = 19)

        // When: メダル適格を判定する
        val result = medalEligibility(OperationType.Multiplication, Level.Normal, quizRange)

        // Then: メダル獲得不可
        assertFalse(result)
    }

    @Test
    fun わり算のデフォルト範囲ではメダル獲得可能() {
        // Given: わり算のデフォルト範囲
        val quizRange = QuizRange.Default(OperationType.Division, Level.Easy)

        // When: メダル適格を判定する
        val result = medalEligibility(OperationType.Division, Level.Easy, quizRange)

        // Then: メダル獲得可能
        assertTrue(result)
    }

    @Test
    fun すべてモードでカスタムminがデフォルトmin未満の場合はメダル獲得不可() {
        // Given: すべてモードの普通レベルのデフォルトminは11、カスタムminを5に下げる
        val quizRange = QuizRange.Custom(OperationType.All, Level.Normal, min = 5, max = 50)

        // When: メダル適格を判定する
        val result = medalEligibility(OperationType.All, Level.Normal, quizRange)

        // Then: メダル獲得不可
        assertFalse(result)
    }

    @Test
    fun すべてモードでカスタムminがデフォルト以上ならメダル獲得可能() {
        // Given: すべてモードの簡単レベルのデフォルトminは1、カスタムminを1以上に設定
        val quizRange = QuizRange.Custom(OperationType.All, Level.Easy, min = 1, max = 10)

        // When: メダル適格を判定する
        val result = medalEligibility(OperationType.All, Level.Easy, quizRange)

        // Then: メダル獲得可能
        assertTrue(result)
    }

    @Test
    fun 難しいレベルでカスタムminが低い場合はメダル獲得不可() {
        // Given: 足し算の難しいレベル（デフォルトmin=101）でカスタムminを50に下げる
        val quizRange = QuizRange.Custom(OperationType.Addition, Level.Difficult, min = 50, max = 9999)

        // When: メダル適格を判定する
        val result = medalEligibility(OperationType.Addition, Level.Difficult, quizRange)

        // Then: メダル獲得不可
        assertFalse(result)
    }
}
