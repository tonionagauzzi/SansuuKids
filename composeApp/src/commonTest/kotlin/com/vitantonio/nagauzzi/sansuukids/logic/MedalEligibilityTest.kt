package com.vitantonio.nagauzzi.sansuukids.logic

import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MedalEligibilityTest {
    private val medalEligibility = MedalEligibility()

    @Test
    fun カスタム範囲なしの場合はメダル獲得可能() {
        // Given: カスタム範囲が空
        val customRanges = emptyList<QuizRange>()

        // When: メダル適格を判定する
        val result = medalEligibility(Mode.Addition, Level.Easy, customRanges)

        // Then: メダル獲得可能
        assertTrue(result)
    }

    @Test
    fun カスタムminがデフォルトmin以上の場合はメダル獲得可能() {
        // Given: カスタムminがデフォルトmin（1）以上
        val customRanges = listOf(
            QuizRange.Custom(OperationType.Addition, Level.Easy, min = 2, max = 5)
        )

        // When: メダル適格を判定する
        val result = medalEligibility(Mode.Addition, Level.Easy, customRanges)

        // Then: メダル獲得可能
        assertTrue(result)
    }

    @Test
    fun カスタムminがデフォルトminと同じ場合はメダル獲得可能() {
        // Given: カスタムminがデフォルトmin（1）と同じ
        val customRanges = listOf(
            QuizRange.Custom(OperationType.Addition, Level.Easy, min = 1, max = 10)
        )

        // When: メダル適格を判定する
        val result = medalEligibility(Mode.Addition, Level.Easy, customRanges)

        // Then: メダル獲得可能
        assertTrue(result)
    }

    @Test
    fun 引き算のカスタムminがデフォルトmin未満の場合はメダル獲得不可() {
        // Given: 引き算の普通レベルのデフォルトminは11、カスタムminを5に下げる
        val customRanges = listOf(
            QuizRange.Custom(OperationType.Subtraction, Level.Normal, min = 5, max = 99)
        )

        // When: メダル適格を判定する
        val result = medalEligibility(Mode.Subtraction, Level.Normal, customRanges)

        // Then: メダル獲得不可
        assertFalse(result)
    }

    @Test
    fun 掛け算のカスタムminがデフォルトmin未満の場合はメダル獲得不可() {
        // Given: 掛け算の普通レベルのデフォルトminは6、カスタムminを3に下げる
        val customRanges = listOf(
            QuizRange.Custom(OperationType.Multiplication, Level.Normal, min = 3, max = 19)
        )

        // When: メダル適格を判定する
        val result = medalEligibility(Mode.Multiplication, Level.Normal, customRanges)

        // Then: メダル獲得不可
        assertFalse(result)
    }

    @Test
    fun わり算モードではかけ算の範囲で判定する() {
        // Given: わり算モードでかけ算のカスタムminを下げる（デフォルトmin=1）
        val customRanges = listOf(
            QuizRange.Custom(OperationType.Multiplication, Level.Easy, min = 1, max = 9)
        )

        // When: メダル適格を判定する
        val result = medalEligibility(Mode.Division, Level.Easy, customRanges)

        // Then: メダル獲得可能（デフォルトと同じ）
        assertTrue(result)
    }

    @Test
    fun すべてモードで一つでもデフォルトmin未満があるとメダル獲得不可() {
        // Given: すべてモードで足し算のカスタムminだけ下げる（足し算Easy デフォルトmin=1）
        val customRanges = listOf(
            QuizRange.Custom(OperationType.Addition, Level.Normal, min = 5, max = 50),
            QuizRange.Custom(OperationType.Subtraction, Level.Normal, min = 11, max = 99),
            QuizRange.Custom(OperationType.Multiplication, Level.Normal, min = 6, max = 19)
        )

        // When: メダル適格を判定する
        val result = medalEligibility(Mode.All, Level.Normal, customRanges)

        // Then: メダル獲得不可（足し算のminがデフォルト11より低い5）
        assertFalse(result)
    }

    @Test
    fun すべてモードで全演算タイプがデフォルト以上ならメダル獲得可能() {
        // Given: すべてモードで全演算タイプのカスタムminがデフォルト以上
        val customRanges = listOf(
            QuizRange.Custom(OperationType.Addition, Level.Easy, min = 1, max = 10),
            QuizRange.Custom(OperationType.Subtraction, Level.Easy, min = 1, max = 15),
            QuizRange.Custom(OperationType.Multiplication, Level.Easy, min = 1, max = 12)
        )

        // When: メダル適格を判定する
        val result = medalEligibility(Mode.All, Level.Easy, customRanges)

        // Then: メダル獲得可能
        assertTrue(result)
    }

    @Test
    fun 難しいレベルでカスタムminが低い場合はメダル獲得不可() {
        // Given: 足し算の難しいレベル（デフォルトmin=101）でカスタムminを50に下げる
        val customRanges = listOf(
            QuizRange.Custom(OperationType.Addition, Level.Difficult, min = 50, max = 9999)
        )

        // When: メダル適格を判定する
        val result = medalEligibility(Mode.Addition, Level.Difficult, customRanges)

        // Then: メダル獲得不可
        assertFalse(result)
    }
}
