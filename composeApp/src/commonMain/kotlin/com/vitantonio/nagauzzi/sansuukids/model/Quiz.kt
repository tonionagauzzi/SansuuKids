package com.vitantonio.nagauzzi.sansuukids.model

import kotlinx.serialization.Serializable

@Serializable
internal data class Quiz(
    val questions: List<Question>,
    val level: Level
) {
    val operationType: OperationType
        get() {
            val types = questions.mapNotNull { question ->
                when (question) {
                    is Question.Math.Addition -> OperationType.Addition
                    is Question.Math.Subtraction -> OperationType.Subtraction
                    is Question.Math.Multiplication -> OperationType.Multiplication
                    is Question.Math.Division -> OperationType.Division
                    is Question.None -> null
                }
            }.toSet()
            return when {
                types.size == 1 -> types.single()
                else -> OperationType.All
            }
        }
}
