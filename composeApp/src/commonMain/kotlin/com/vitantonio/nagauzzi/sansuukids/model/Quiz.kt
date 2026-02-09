package com.vitantonio.nagauzzi.sansuukids.model

import kotlinx.serialization.Serializable

@Serializable
internal data class Quiz(
    val questions: List<Question>,
    val operationType: OperationType,
    val level: Level
)
