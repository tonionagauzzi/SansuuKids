package com.vitantonio.nagauzzi.sansuukids.model

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Stable
internal class QuizState(
    private val quiz: Quiz
) {
    var currentQuestionIndex: Int by mutableIntStateOf(0)
        private set

    var currentInput: String by mutableStateOf("")
        private set

    private val _userAnswers = mutableStateListOf<UserAnswer>()
    val userAnswers: List<UserAnswer> get() = _userAnswers.toList()

    val currentQuestion: Question
        get() = quiz.questions[currentQuestionIndex]

    val totalQuestions: List<Question>
        get() = quiz.questions

    val progress: Float
        get() = (currentQuestionIndex + 1) / quiz.questions.size.toFloat()

    val isQuizComplete: Boolean
        get() = _userAnswers.size == quiz.questions.size

    val answeredCount: Int
        get() = _userAnswers.size

    fun appendDigit(digit: Int) {
        require(digit in 0..9) { "Digit must be between 0 and 9" }
        if (currentInput.length < quiz.maxInputLength) {
            currentInput += digit.toString()
        }
    }

    fun deleteLastDigit() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
        }
    }

    fun submitAnswer(): Boolean {
        val answer = currentInput.toIntOrNull()
        val isCorrect = answer == currentQuestion.correctAnswer

        _userAnswers.add(
            UserAnswer(
                questionIndex = currentQuestionIndex,
                answer = answer,
                isCorrect = isCorrect
            )
        )

        currentInput = ""

        if (currentQuestionIndex < quiz.questions.size - 1) {
            currentQuestionIndex++
        }

        return isCorrect
    }

    fun toResult(): QuizResult = QuizResult(quiz, _userAnswers.toList())
}
