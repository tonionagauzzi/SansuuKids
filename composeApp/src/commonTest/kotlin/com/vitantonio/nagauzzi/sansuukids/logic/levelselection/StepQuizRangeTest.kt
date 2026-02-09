package com.vitantonio.nagauzzi.sansuukids.logic.levelselection

import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import kotlin.test.Test
import kotlin.test.assertEquals

class StepQuizRangeTest {

    @Test
    fun step1の場合は値がそのままスナップされる() {
        // Given: Addition/Easy → step=1, maxValue=20
        val quizRange = QuizRange.Default(OperationType.Addition, Level.Easy)

        // When/Then: 各値がそのままスナップされる（maxは最大値に固定）
        assertEquals(1, stepQuizRange(quizRange, 1f, 20f).min)
        assertEquals(5, stepQuizRange(quizRange, 5f, 20f).min)
        assertEquals(10, stepQuizRange(quizRange, 10f, 20f).min)
        assertEquals(19, stepQuizRange(quizRange, 19f, 20f).min)
    }

    @Test
    fun step10の場合は最も近いステップにスナップされる() {
        // Given: Multiplication/Difficult → step=10, maxValue=200
        val quizRange = QuizRange.Default(OperationType.Multiplication, Level.Difficult)

        // When/Then: 値が最も近いステップにスナップされる（maxは最大値に固定）
        assertEquals(10, stepQuizRange(quizRange, 11f, 200f).min)
        assertEquals(20, stepQuizRange(quizRange, 15f, 200f).min)
        assertEquals(20, stepQuizRange(quizRange, 19f, 200f).min)
        assertEquals(20, stepQuizRange(quizRange, 21f, 200f).min)
        assertEquals(100, stepQuizRange(quizRange, 101f, 200f).min)
    }

    @Test
    fun step100の場合は最も近いステップにスナップされる() {
        // Given: Addition/Difficult → step=100, maxValue=10000
        val quizRange = QuizRange.Default(OperationType.Addition, Level.Difficult)

        // When/Then: 値が最も近いステップにスナップされる（maxは最大値に固定）
        assertEquals(1, stepQuizRange(quizRange, 1f, 10000f).min)
        assertEquals(100, stepQuizRange(quizRange, 101f, 10000f).min)
        assertEquals(500, stepQuizRange(quizRange, 450f, 10000f).min)
        assertEquals(500, stepQuizRange(quizRange, 501f, 10000f).min)
    }

    @Test
    fun 最小値の境界テスト() {
        // Given: Multiplication/Difficult → step=10, maxValue=200
        val quizRange = QuizRange.Default(OperationType.Multiplication, Level.Difficult)

        // When/Then: 最小値付近の値が正しくスナップされる（maxは最大値に固定）
        assertEquals(1, stepQuizRange(quizRange, 1f, 200f).min)
        assertEquals(1, stepQuizRange(quizRange, 2f, 200f).min)
        assertEquals(10, stepQuizRange(quizRange, 5f, 200f).min)
        assertEquals(10, stepQuizRange(quizRange, 6f, 200f).min)
    }

    @Test
    fun 最大値の境界テスト() {
        // Given: Multiplication/Difficult → step=10, maxValue=200
        val quizRange = QuizRange.Default(OperationType.Multiplication, Level.Difficult)

        // When/Then: 最大値付近の値が正しくスナップされる（minは最小値に固定）
        assertEquals(190, stepQuizRange(quizRange, 10f, 191f).max)
        assertEquals(200, stepQuizRange(quizRange, 10f, 199f).max)
    }
}
