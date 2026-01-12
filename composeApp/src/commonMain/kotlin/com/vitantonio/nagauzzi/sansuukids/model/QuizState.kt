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

    val progress: Float
        get() = (currentQuestionIndex + 1) / Quiz.QUIZ_SIZE.toFloat()

    val isQuizComplete: Boolean
        get() = _userAnswers.size == Quiz.QUIZ_SIZE

    val answeredCount: Int
        get() = _userAnswers.size

    fun appendDigit(digit: Int) {
        require(digit in 0..9) { "Digit must be between 0 and 9" }
        if (currentInput.length < MAX_INPUT_LENGTH) {
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

        if (currentQuestionIndex < Quiz.QUIZ_SIZE - 1) {
            currentQuestionIndex++
        }

        return isCorrect
    }

    fun toResult(): QuizResult = QuizResult(quiz, _userAnswers.toList())

    companion object {
        // 解答として入力を許可する最大桁数。
        // 現状は最大値が足し算の 9999 + 9999 = 19998 を超えることがないため、最大 5 桁を設定している。
        // 問題の難易度を変更して、これより大きな値を扱う場合はこの値も合わせて見直す必要がある。
        private const val MAX_INPUT_LENGTH = 5
    }
}
