package com.vitantonio.nagauzzi.sansuukids.model

import kotlinx.serialization.Serializable

@Serializable
internal data class Question(
    val leftOperand: Int,
    val rightOperand: Int,
    val operator: Operator,
    val correctAnswer: Int
) {
    val displayText: String
        get() = "$leftOperand ${operator.symbol} $rightOperand = ?"
}
