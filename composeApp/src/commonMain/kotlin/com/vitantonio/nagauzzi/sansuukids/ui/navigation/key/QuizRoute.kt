package com.vitantonio.nagauzzi.sansuukids.ui.navigation.key

import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import kotlinx.serialization.Serializable

@Serializable
internal data class QuizRoute(
    val operationType: OperationType,
    val level: Level,
    val quizRange: QuizRange
) : SansuuKidsRoute
