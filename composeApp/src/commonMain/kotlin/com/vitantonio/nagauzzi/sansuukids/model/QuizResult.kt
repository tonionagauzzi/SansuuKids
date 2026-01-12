package com.vitantonio.nagauzzi.sansuukids.model

import kotlinx.serialization.Serializable

@Serializable
internal data class UserAnswer(
    val questionIndex: Int,
    val answer: Int?,
    val isCorrect: Boolean
)

@Serializable
internal data class QuizResult(
    val quiz: Quiz,
    val userAnswers: List<UserAnswer>
) {
    val correctCount: Int
        get() = userAnswers.count { it.isCorrect }
}
