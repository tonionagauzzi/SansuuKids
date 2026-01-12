package com.vitantonio.nagauzzi.sansuukids.model

import kotlin.test.Test
import kotlin.test.assertEquals

class QuizTest {

    @Test
    fun `Quizが正しい入力最大値を返す`() {
        // Given: 四則演算それぞれを持つQuizを設定
        val quiz = Quiz(
            questions = listOf(
                Question(5, 3, Operator.ADDITION, 8),
                Question(12, 7, Operator.SUBTRACTION, 5),
                Question(9, 12, Operator.MULTIPLICATION, 108),
                Question(100, 4, Operator.DIVISION, 25)
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
