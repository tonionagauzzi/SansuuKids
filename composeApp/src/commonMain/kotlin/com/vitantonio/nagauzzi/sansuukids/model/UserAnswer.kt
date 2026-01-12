package com.vitantonio.nagauzzi.sansuukids.model

import kotlinx.serialization.Serializable

@Serializable
internal data class UserAnswer(
    val questionIndex: Int,
    val answer: Int,
    val isCorrect: Boolean
)
