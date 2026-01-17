package com.vitantonio.nagauzzi.sansuukids.ui.navigation.key

import com.vitantonio.nagauzzi.sansuukids.model.Question
import com.vitantonio.nagauzzi.sansuukids.model.UserAnswer
import kotlinx.serialization.Serializable

@Serializable
internal data class AnswerCheckRoute(
    val questions: List<Question>,
    val userAnswers: List<UserAnswer>
) : SansuuKidsRoute
