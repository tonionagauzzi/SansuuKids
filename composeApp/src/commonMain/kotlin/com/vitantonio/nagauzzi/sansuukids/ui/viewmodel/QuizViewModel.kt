package com.vitantonio.nagauzzi.sansuukids.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.vitantonio.nagauzzi.sansuukids.logic.GenerateQuiz
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.model.QuizState
import com.vitantonio.nagauzzi.sansuukids.model.UserAnswer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class QuizViewModel(
    mode: Mode,
    level: Level
) : ViewModel() {
    private val generateQuiz = GenerateQuiz()
    private val quiz = generateQuiz(mode, level)

    val mutableQuizState = MutableStateFlow(QuizState(quiz))
    val quizState: StateFlow<QuizState> = mutableQuizState

    private var currentQuizState: QuizState
        get() = mutableQuizState.value
        set(value) {
            mutableQuizState.value = value
        }

    fun appendDigit(digit: Int) {
        require(digit in 0..9) { "Digit must be between 0 and 9" }
        if (currentQuizState.currentInput.length < quiz.maxInputLength) {
            currentQuizState = currentQuizState.copy(
                currentInput = currentQuizState.currentInput + digit.toString()
            )
        }
    }

    fun deleteLastDigit() {
        if (currentQuizState.currentInput.isNotEmpty()) {
            currentQuizState = currentQuizState.copy(
                currentInput = currentQuizState.currentInput.dropLast(1)
            )
        }
    }

    fun submitAnswer() {
        val answer = currentQuizState.currentInput.toIntOrNull()
        val isCorrect = answer == currentQuizState.currentQuestion.correctAnswer
        val nextQuestionIndex = if (currentQuizState.currentQuestionIndex < quiz.questions.size - 1) {
            currentQuizState.currentQuestionIndex + 1
        } else {
            currentQuizState.currentQuestionIndex
        }

        currentQuizState = currentQuizState.copy(
            currentQuestionIndex = nextQuestionIndex,
            currentInput = "",
            userAnswers = currentQuizState.userAnswers + UserAnswer(
                questionIndex = currentQuizState.currentQuestionIndex,
                answer = answer,
                isCorrect = isCorrect
            )
        )
    }
}
