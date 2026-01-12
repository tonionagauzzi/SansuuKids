package com.vitantonio.nagauzzi.sansuukids.model

import kotlin.test.Test
import kotlin.test.assertEquals

class QuizTest {

    @Test
    fun `Quizが正しい入力最大値を返す`() {
        // Given: 四則演算それぞれを持つQuizを設定
        val quiz = Quiz(
            questions = listOf(
                Question.Addition(5, 3),         // correctAnswer = 8
                Question.Subtraction(12, 7),     // correctAnswer = 5
                Question.Multiplication(9, 12),  // correctAnswer = 108
                Question.Division(100, 4)        // correctAnswer = 25
            ),
            mode = Mode.ALL,
            level = Level.NORMAL
        )

        // When: Quizから最大値を取得
        val maxInputLength = quiz.maxInputLength

        // Then: 正しい最大値が返される
        assertEquals(3, maxInputLength) // 9 * 12 = 108 の長さは 3
    }
}
