package com.vitantonio.nagauzzi.sansuukids.ui.navigation.key

import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import kotlinx.serialization.Serializable

@Serializable
internal data class QuizRoute(val quizRange: QuizRange) : SansuuKidsRoute
