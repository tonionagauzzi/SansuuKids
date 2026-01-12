package com.vitantonio.nagauzzi.sansuukids.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal sealed interface Question {
    val displayText: String

    @Serializable
    @SerialName("none")
    data object None : Question {
        override val displayText: String = ""
    }

    @Serializable
    @SerialName("math")
    sealed interface Math : Question {
        val correctAnswer: Int

        @Serializable
        @SerialName("addition")
        data class Addition(
            val leftOperand: Int,
            val rightOperand: Int
        ) : Math {
            override val displayText: String get() = "$leftOperand + $rightOperand = ?"
            override val correctAnswer: Int get() = leftOperand + rightOperand
        }

        @Serializable
        @SerialName("subtraction")
        data class Subtraction(
            val leftOperand: Int,
            val rightOperand: Int
        ) : Math {
            override val displayText: String get() = "$leftOperand - $rightOperand = ?"
            override val correctAnswer: Int get() = leftOperand - rightOperand
        }

        @Serializable
        @SerialName("multiplication")
        data class Multiplication(
            val leftOperand: Int,
            val rightOperand: Int
        ) : Math {
            override val displayText: String get() = "$leftOperand × $rightOperand = ?"
            override val correctAnswer: Int get() = leftOperand * rightOperand
        }

        @Serializable
        @SerialName("division")
        data class Division(
            val dividend: Int,
            val divisor: Int
        ) : Math {
            override val displayText: String get() = "$dividend ÷ $divisor = ?"
            override val correctAnswer: Int get() = dividend / divisor
        }
    }
}
