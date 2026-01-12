package com.vitantonio.nagauzzi.sansuukids.model

import kotlinx.serialization.Serializable

@Serializable
internal data class Quiz(
    val questions: List<Question>,
    val mode: Mode,
    val level: Level
) {
    val maxInputLength: Int
        get() = questions.maxOf { question ->
            question.correctAnswer
        }.toString().length
}
