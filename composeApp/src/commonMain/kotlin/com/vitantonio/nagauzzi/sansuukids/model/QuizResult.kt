package com.vitantonio.nagauzzi.sansuukids.model

import kotlinx.serialization.Serializable

@Serializable
internal data class UserAnswer(
    val questionIndex: Int,
    val answer: Int,
    val isCorrect: Boolean
)

/**
 * クイズの結果を表すクラス。
 *
 * @property quiz 出題されたクイズ
 * @property userAnswers ユーザーの回答リスト
 */
@Serializable
internal data class QuizResult(
    val quiz: Quiz,
    val userAnswers: List<UserAnswer>
) {
    /**
     * 正解した問題の数。
     */
    val correctCount: Int
        get() = userAnswers.count { it.isCorrect }
}
