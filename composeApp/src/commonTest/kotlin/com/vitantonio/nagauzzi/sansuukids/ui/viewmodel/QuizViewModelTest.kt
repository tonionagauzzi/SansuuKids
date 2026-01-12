package com.vitantonio.nagauzzi.sansuukids.ui.viewmodel

import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class QuizViewModelTest {

    @Test
    fun ViewModelを初期化するとQuizStateが作成される() {
        // Given: ADDITIONモードのEASYレベル

        // When: ViewModelを初期化する
        val viewModel = QuizViewModel(Mode.ADDITION, Level.EASY)

        // Then: QuizStateが作成され、最初の問題が表示される
        assertEquals(0, viewModel.quizState.currentQuestionIndex)
        assertFalse(viewModel.quizState.isQuizComplete)
    }

    @Test
    fun 異なるモードとレベルでViewModelを作成できる() {
        // Given/When: 異なるモードとレベルの組み合わせでViewModelを作成する
        val viewModelAdditionEasy = QuizViewModel(Mode.ADDITION, Level.EASY)
        val viewModelSubtractionNormal = QuizViewModel(Mode.SUBTRACTION, Level.NORMAL)
        val viewModelMultiplicationDifficult = QuizViewModel(Mode.MULTIPLICATION, Level.DIFFICULT)

        // Then: それぞれ正常に初期化される
        assertEquals(10, viewModelAdditionEasy.quizState.totalQuestions.size)
        assertEquals(10, viewModelSubtractionNormal.quizState.totalQuestions.size)
        assertEquals(10, viewModelMultiplicationDifficult.quizState.totalQuestions.size)
    }
}
