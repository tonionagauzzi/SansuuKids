package com.vitantonio.nagauzzi.sansuukids.ui.viewmodel

import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class QuizViewModelTest {

    @Test
    fun ViewModelを初期化するとQuizStateが作成される() {
        // Given: ADDITIONモードのEASYレベル

        // When: ViewModelを初期化する
        val viewModel = QuizViewModel(Mode.ADDITION, Level.EASY)

        // Then: QuizStateが作成され、最初の問題が表示される
        assertEquals(0, viewModel.quizState.value.currentQuestionIndex)
        assertFalse(viewModel.quizState.value.isQuizComplete)
    }

    @Test
    fun 異なるモードとレベルでViewModelを作成できる() {
        // Given/When: 異なるモードとレベルの組み合わせでViewModelを作成する
        val viewModelAdditionEasy = QuizViewModel(Mode.ADDITION, Level.EASY)
        val viewModelSubtractionNormal = QuizViewModel(Mode.SUBTRACTION, Level.NORMAL)
        val viewModelMultiplicationDifficult = QuizViewModel(Mode.MULTIPLICATION, Level.DIFFICULT)

        // Then: それぞれ正常に初期化される
        assertEquals(10, viewModelAdditionEasy.quizState.value.totalQuestions.size)
        assertEquals(10, viewModelSubtractionNormal.quizState.value.totalQuestions.size)
        assertEquals(10, viewModelMultiplicationDifficult.quizState.value.totalQuestions.size)
    }

    @Test
    fun appendDigitで数字を入力できる() {
        // Given: ViewModelを初期化する
        val viewModel = QuizViewModel(Mode.ADDITION, Level.EASY)

        // When: 数字を入力する
        viewModel.appendDigit(5)

        // Then: 入力が反映される
        assertEquals("5", viewModel.quizState.value.currentInput)
    }

    @Test
    fun appendDigitで負の数値を入力すると例外がスローされる() {
        // Given: ViewModelを初期化する
        val viewModel = QuizViewModel(Mode.ADDITION, Level.EASY)

        // When & Then: -1を入力しようとすると例外がスローされる
        assertFailsWith<IllegalArgumentException> {
            viewModel.appendDigit(-1)
        }
    }

    @Test
    fun appendDigitで10以上の数値を一度に入力すると例外がスローされる() {
        // Given: ViewModelを初期化する
        val viewModel = QuizViewModel(Mode.ADDITION, Level.EASY)

        // When & Then: 10を入力しようとすると例外がスローされる
        assertFailsWith<IllegalArgumentException> {
            viewModel.appendDigit(10)
        }
    }

    @Test
    fun appendDigitで複数桁の数字を入力できる() {
        // Given: DIFFICULTレベルのViewModelを作成する（最大入力桁数が大きい）
        val viewModel = QuizViewModel(Mode.ADDITION, Level.DIFFICULT)

        // When: 複数の数字を入力する
        viewModel.appendDigit(1)
        viewModel.appendDigit(2)
        viewModel.appendDigit(3)

        // Then: 入力が連結される
        assertEquals("123", viewModel.quizState.value.currentInput)
    }

    @Test
    fun deleteLastDigitで最後の数字を削除できる() {
        // Given: NORMALレベルのViewModelを作成して数字を入力
        val viewModel = QuizViewModel(Mode.ADDITION, Level.NORMAL)
        viewModel.appendDigit(1)
        viewModel.appendDigit(2)

        // When: 最後の数字を削除する
        viewModel.deleteLastDigit()

        // Then: 最後の数字が削除される
        assertEquals("1", viewModel.quizState.value.currentInput)
    }

    @Test
    fun deleteLastDigitで空の入力には何も起こらない() {
        // Given: 空の入力のViewModel
        val viewModel = QuizViewModel(Mode.ADDITION, Level.EASY)

        // When: 削除を試みる
        viewModel.deleteLastDigit()

        // Then: 入力は空のまま
        assertEquals("", viewModel.quizState.value.currentInput)
    }

    @Test
    fun submitAnswerで正解判定が行われる_正解の場合() {
        // Given: ViewModelを初期化する
        val viewModel = QuizViewModel(Mode.ADDITION, Level.EASY)
        val currentMathQuestion = viewModel.quizState.value.currentQuestion as Math
        val correctAnswer = currentMathQuestion.correctAnswer

        // When: 正解を入力して提出する
        correctAnswer.toString().forEach { digit ->
            viewModel.appendDigit(digit.digitToInt())
        }
        viewModel.submitAnswer()

        // Then: 正解と判定される
        assertEquals(1, viewModel.quizState.value.userAnswers.size)
        assertTrue(viewModel.quizState.value.userAnswers[0].isCorrect)
    }

    @Test
    fun submitAnswerで正解判定が行われる_不正解の場合() {
        // Given: ViewModelを初期化する
        val viewModel = QuizViewModel(Mode.ADDITION, Level.EASY)
        val currentMathQuestion = viewModel.quizState.value.currentQuestion as Math
        val correctAnswer = currentMathQuestion.correctAnswer
        val wrongAnswer = correctAnswer + 1 // 確実に不正解になる値

        // When: 不正解を入力して提出する
        wrongAnswer.toString().forEach { digit ->
            viewModel.appendDigit(digit.digitToInt())
        }
        viewModel.submitAnswer()

        // Then: 不正解と判定される
        assertEquals(1, viewModel.quizState.value.userAnswers.size)
        assertFalse(viewModel.quizState.value.userAnswers[0].isCorrect)
    }

    @Test
    fun submitAnswerで次の問題に進む() {
        // Given: ViewModelを初期化する
        val viewModel = QuizViewModel(Mode.ADDITION, Level.EASY)

        // When: 回答を提出する
        viewModel.appendDigit(1)
        viewModel.submitAnswer()

        // Then: 次の問題に進む
        assertEquals(1, viewModel.quizState.value.currentQuestionIndex)
    }

    @Test
    fun submitAnswer後に入力がクリアされる() {
        // Given: 入力があるViewModel
        val viewModel = QuizViewModel(Mode.ADDITION, Level.EASY)
        viewModel.appendDigit(2)

        // When: 回答を提出する
        viewModel.submitAnswer()

        // Then: 入力がクリアされる
        assertEquals("", viewModel.quizState.value.currentInput)
    }

    @Test
    fun 全問回答するとisQuizCompleteがtrueになる() {
        // Given: ViewModelを初期化する
        val viewModel = QuizViewModel(Mode.ADDITION, Level.EASY)
        val quizSize = viewModel.quizState.value.totalQuestions.size

        // When: 全問全て回答する
        repeat(quizSize) {
            viewModel.appendDigit(1)
            viewModel.submitAnswer()
        }

        // Then: クイズが完了する
        assertTrue(viewModel.quizState.value.isQuizComplete)
        assertEquals(quizSize, viewModel.quizState.value.answeredCount)
    }

    @Test
    fun toResultで正しいQuizResultが生成される() {
        // Given: いくつか回答したViewModel
        val viewModel = QuizViewModel(Mode.ADDITION, Level.EASY)
        val currentMathQuestion = viewModel.quizState.value.currentQuestion as Math
        val correctAnswer = currentMathQuestion.correctAnswer

        // 1問目: 正解
        correctAnswer.toString().forEach { digit ->
            viewModel.appendDigit(digit.digitToInt())
        }
        viewModel.submitAnswer()

        // 2問目: 不正解
        viewModel.appendDigit(0)
        viewModel.submitAnswer()

        // When: 結果を生成する
        val result = viewModel.quizState.value.toResult()

        // Then: 正しいQuizResultが生成される
        assertEquals(2, result.userAnswers.size)
        assertEquals(1, result.correctCount)
    }

    @Test
    fun 決定ボタンの有効状態は入力に応じて変化する() {
        // Given: ViewModelを初期化する
        val viewModel = QuizViewModel(Mode.ADDITION, Level.EASY)

        // When: 初期状態
        // Then: 入力が空なので無効
        assertTrue(viewModel.quizState.value.currentInput.isEmpty())

        // When: 数字を入力する
        viewModel.appendDigit(5)

        // Then: 入力があるので有効
        assertTrue(viewModel.quizState.value.currentInput.isNotEmpty())

        // When: 数字を削除する
        viewModel.deleteLastDigit()

        // Then: 入力が空なので無効
        assertTrue(viewModel.quizState.value.currentInput.isEmpty())
    }
}
