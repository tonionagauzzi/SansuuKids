package com.vitantonio.nagauzzi.sansuukids.ui.navigation.key

import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.model.Question
import com.vitantonio.nagauzzi.sansuukids.model.UserAnswer
import kotlinx.serialization.Serializable

@Serializable
internal data class ResultRoute(
    val mode: Mode,
    val level: Level,
    val score: Int,
    val medal: Medal,
    val questions: List<Question>,
    val userAnswers: List<UserAnswer>
) : SansuuKidsRoute
