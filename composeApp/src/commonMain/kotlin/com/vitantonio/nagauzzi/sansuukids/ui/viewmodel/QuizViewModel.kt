package com.vitantonio.nagauzzi.sansuukids.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.vitantonio.nagauzzi.sansuukids.logic.AwardMedal
import com.vitantonio.nagauzzi.sansuukids.logic.CalculateScore
import com.vitantonio.nagauzzi.sansuukids.logic.GenerateQuiz
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math
import com.vitantonio.nagauzzi.sansuukids.model.QuizState
import com.vitantonio.nagauzzi.sansuukids.model.UserAnswer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class QuizViewModel(
    mode: Mode,
    level: Level
) : ViewModel() {
    private val generateQuiz = GenerateQuiz()
    private val calculateScore = CalculateScore()
    private val awardMedal = AwardMedal()

    private val mutableQuizState = MutableStateFlow(QuizState(generateQuiz(mode, level)))
    val quizState: StateFlow<QuizState> = mutableQuizState

    private var currentQuizState: QuizState
        get() = mutableQuizState.value
        set(value) {
            mutableQuizState.value = value
        }

    val earnedScore: Int
        get() = calculateScore(
            correctCount = currentQuizState.correctCount,
            totalCount = currentQuizState.totalQuestions.size
        )

    val earnedMedal: Medal
        get() = awardMedal(
            isQuizComplete = currentQuizState.isQuizComplete,
            correctCount = currentQuizState.correctCount,
            totalCount = currentQuizState.totalQuestions.size
        )

    fun appendDigit(digit: Int) {
        require(digit in 0..9) { "Digit must be between 0 and 9" }
        if (currentQuizState.isAppendDigitEnabled) {
            val currentInput = currentQuizState.currentInput ?: 0
            currentQuizState = currentQuizState.copy(
                currentInput = (currentInput.toString() + digit.toString()).toIntOrNull()
            )
        }
    }

    fun deleteLastDigit() {
        currentQuizState = currentQuizState.copy(
            currentInput = currentQuizState.currentInput.toString().dropLast(1).toIntOrNull()
        )
    }

    fun submitAnswer() {
        // 有効な回答が入力されていない場合は送信しない
        val answer = currentQuizState.currentInput ?: return
        val currentMathQuestion = currentQuizState.currentQuestion as? Math ?: return

        // 回答が正しいかどうかを判定
        val isCorrect = answer == currentMathQuestion.correctAnswer

        currentQuizState = currentQuizState.copy(
            currentInput = null,
            userAnswers = currentQuizState.userAnswers + UserAnswer(
                questionIndex = currentQuizState.currentQuestionIndex,
                answer = answer,
                isCorrect = isCorrect
            )
        )
    }

    fun cancelLastAnswer() {
        val userAnswers = currentQuizState.userAnswers
        if (userAnswers.isNotEmpty()) {
            currentQuizState = currentQuizState.copy(
                userAnswers = userAnswers.dropLast(1)
            )
        }
    }
}
