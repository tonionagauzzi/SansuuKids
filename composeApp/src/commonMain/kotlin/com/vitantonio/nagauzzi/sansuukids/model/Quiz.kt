package com.vitantonio.nagauzzi.sansuukids.model

import kotlinx.serialization.Serializable

@Serializable
internal data class Quiz(
    val questions: List<Question>,
    val mode: Mode,
    val level: Level
) {
    init {
        require(questions.size == QUIZ_SIZE) { "Quiz must have exactly $QUIZ_SIZE questions" }
    }

    companion object {
        const val QUIZ_SIZE = 10
    }
}
