package com.vitantonio.nagauzzi.sansuukids.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.vitantonio.nagauzzi.sansuukids.logic.quiz.AwardMedal
import com.vitantonio.nagauzzi.sansuukids.logic.quiz.CalculateScore
import com.vitantonio.nagauzzi.sansuukids.logic.quiz.GenerateQuiz
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import com.vitantonio.nagauzzi.sansuukids.model.QuizState
import com.vitantonio.nagauzzi.sansuukids.model.UserAnswer
import com.vitantonio.nagauzzi.sansuukids.model.isMedalEnabled
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class QuizViewModel(
    private val quizRange: QuizRange
) : ViewModel() {
    private val generateQuiz = GenerateQuiz()
    private val calculateScore = CalculateScore()
    private val awardMedal = AwardMedal()

    private val operationType get() = quizRange.operationType
    private val level get() = quizRange.level

    private val mutableQuizState =
        MutableStateFlow(QuizState(generateQuiz(operationType, level, quizRange)))
    val quizState: StateFlow<QuizState> = mutableQuizState

    private var currentQuizState: QuizState
        get() = mutableQuizState.value
        set(value) {
            mutableQuizState.value = value
        }

    val earnedScore: Int
        get() = calculateScore(
            correctCount = currentQuizState.correctCount,
            totalCount = currentQuizState.quiz.questions.size
        )

    val earnedMedal: Medal
        get() = if (quizRange.isMedalEnabled) {
            awardMedal(
                isQuizComplete = currentQuizState.isQuizComplete,
                correctCount = currentQuizState.correctCount,
                totalCount = currentQuizState.quiz.questions.size
            )
        } else {
            Medal.Nothing
        }

    fun appendDigit(digit: Int) {
        require(digit in 0..9) { "Digit must be between 0 and 9" }
        if (currentQuizState.isAppendDigitEnabled) {
            val currentInputString = currentQuizState.currentInput?.toString() ?: ""
            val newInput = (currentInputString + digit.toString()).toIntOrNull() ?: return
            currentQuizState = currentQuizState.copy(currentInput = newInput)
        }
    }

    fun deleteLastDigit() {
        currentQuizState = currentQuizState.copy(
            currentInput = currentQuizState.currentInput?.toString()?.dropLast(1)?.toIntOrNull()
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
