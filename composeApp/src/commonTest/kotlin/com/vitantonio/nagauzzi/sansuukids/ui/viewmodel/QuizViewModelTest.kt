package com.vitantonio.nagauzzi.sansuukids.ui.viewmodel

import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class QuizViewModelTest {

    private fun createViewModel(
        operationType: OperationType = OperationType.Addition,
        level: Level = Level.Easy
    ): QuizViewModel {
        val quizRange = QuizRange.Default(operationType, level)
        return QuizViewModel(operationType, level, quizRange)
    }

    @Test
    fun ViewModelを初期化するとQuizStateが作成される() {
        // Given: 足し算モードの簡単レベル

        // When: ViewModelを初期化する
        val viewModel = createViewModel(OperationType.Addition, Level.Easy)

        // Then: QuizStateが作成され、最初の問題が表示される
        assertEquals(0, viewModel.quizState.value.currentQuestionIndex)
        assertFalse(viewModel.quizState.value.isQuizComplete)
    }

    @Test
    fun 異なるモードとレベルでViewModelを作成できる() {
        // Given/When: 異なるモードとレベルの組み合わせでViewModelを作成する
        val viewModelAdditionEasy = createViewModel(OperationType.Addition, Level.Easy)
        val viewModelSubtractionNormal = createViewModel(OperationType.Subtraction, Level.Normal)
        val viewModelMultiplicationDifficult = createViewModel(OperationType.Multiplication, Level.Difficult)

        // Then: それぞれ正常に初期化される
        assertEquals(10, viewModelAdditionEasy.quizState.value.quiz.questions.size)
        assertEquals(10, viewModelSubtractionNormal.quizState.value.quiz.questions.size)
        assertEquals(10, viewModelMultiplicationDifficult.quizState.value.quiz.questions.size)
    }

    @Test
    fun appendDigitで数字を入力できる() {
        // Given: ViewModelを初期化する
        val viewModel = createViewModel(OperationType.Addition, Level.Easy)

        // When: 数字を入力する
        viewModel.appendDigit(5)

        // Then: 入力が反映される
        assertEquals(5, viewModel.quizState.value.currentInput)
    }

    @Test
    fun appendDigitで負の数値を入力すると例外がスローされる() {
        // Given: ViewModelを初期化する
        val viewModel = createViewModel(OperationType.Addition, Level.Easy)

        // When & Then: -1を入力しようとすると例外がスローされる
        assertFailsWith<IllegalArgumentException> {
            viewModel.appendDigit(-1)
        }
    }

    @Test
    fun appendDigitで10以上の数値を一度に入力すると例外がスローされる() {
        // Given: ViewModelを初期化する
        val viewModel = createViewModel(OperationType.Addition, Level.Easy)

        // When & Then: 10を入力しようとすると例外がスローされる
        assertFailsWith<IllegalArgumentException> {
            viewModel.appendDigit(10)
        }
    }

    @Test
    fun appendDigitで複数桁の数字を入力できる() {
        // Given: 難しいレベルのViewModelを作成する（最大入力桁数が大きい）
        val viewModel = createViewModel(OperationType.Addition, Level.Difficult)

        // When: 複数の数字を入力する
        viewModel.appendDigit(1)
        viewModel.appendDigit(2)
        viewModel.appendDigit(3)

        // Then: 入力が連結される
        assertEquals(123, viewModel.quizState.value.currentInput)
    }

    @Test
    fun deleteLastDigitで最後の数字を削除できる() {
        // Given: 普通レベルのViewModelを作成して数字を入力
        val viewModel = createViewModel(OperationType.Addition, Level.Normal)
        viewModel.appendDigit(1)
        viewModel.appendDigit(2)

        // When: 最後の数字を削除する
        viewModel.deleteLastDigit()

        // Then: 最後の数字が削除される
        assertEquals(1, viewModel.quizState.value.currentInput)
    }

    @Test
    fun deleteLastDigitで何も入力していない場合は何も起こらない() {
        // Given: 何も入力していないViewModel
        val viewModel = createViewModel(OperationType.Addition, Level.Easy)

        // When: 削除を試みる
        viewModel.deleteLastDigit()

        // Then: 入力はnullのまま
        assertEquals(null, viewModel.quizState.value.currentInput)
    }

    @Test
    fun submitAnswerで正解判定が行われる_正解の場合() {
        // Given: ViewModelを初期化する
        val viewModel = createViewModel(OperationType.Addition, Level.Easy)
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
        val viewModel = createViewModel(OperationType.Addition, Level.Easy)
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
        val viewModel = createViewModel(OperationType.Addition, Level.Easy)

        // When: 回答を提出する
        viewModel.appendDigit(1)
        viewModel.submitAnswer()

        // Then: 次の問題に進む
        assertEquals(1, viewModel.quizState.value.currentQuestionIndex)
    }

    @Test
    fun submitAnswer後に入力がクリアされる() {
        // Given: 入力があるViewModel
        val viewModel = createViewModel(OperationType.Addition, Level.Easy)
        viewModel.appendDigit(2)

        // When: 回答を提出する
        viewModel.submitAnswer()

        // Then: 入力がクリアされる
        assertEquals(null, viewModel.quizState.value.currentInput)
    }

    @Test
    fun 全問回答するとisQuizCompleteがtrueになる() {
        // Given: ViewModelを初期化する
        val viewModel = createViewModel(OperationType.Addition, Level.Easy)
        val quizSize = viewModel.quizState.value.quiz.questions.size

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
    fun 決定ボタンの有効状態は入力に応じて変化する() {
        // Given: ViewModelを初期化する
        val viewModel = createViewModel(OperationType.Addition, Level.Easy)

        // When: 初期状態
        // Then: 入力が空なので無効
        assertNull(viewModel.quizState.value.currentInput)

        // When: 数字を入力する
        viewModel.appendDigit(5)

        // Then: 入力があるので有効
        assertNotNull(viewModel.quizState.value.currentInput)

        // When: 数字を削除する
        viewModel.deleteLastDigit()

        // Then: 入力が空なので無効
        assertNull(viewModel.quizState.value.currentInput)
    }

    @Test
    fun earnedScoreとearnedMedalは正答率に応じた得点とメダルを返す() {
        // Given: ViewModelを初期化する
        val viewModel = createViewModel(OperationType.Addition, Level.Easy)
        val quizSize = viewModel.quizState.value.quiz.questions.size

        // When: 全問正解する
        repeat(quizSize) {
            val currentMathQuestion = viewModel.quizState.value.currentQuestion as Math
            val correctAnswer = currentMathQuestion.correctAnswer
            correctAnswer.toString().forEach { digit ->
                viewModel.appendDigit(digit.digitToInt())
            }
            viewModel.submitAnswer()
        }

        // Then: 100点とゴールドメダルが獲得できる
        assertEquals(100, viewModel.earnedScore)
        assertEquals(Medal.Gold, viewModel.earnedMedal)
    }

    @Test
    fun cancelLastAnswerで回答を取り消し再度回答できる() {
        // Given: 3つ回答済みのViewModel
        val viewModel = createViewModel(OperationType.Addition, Level.Easy)
        repeat(3) {
            viewModel.appendDigit(1)
            viewModel.submitAnswer()
        }

        // When: cancelLastAnswerを3回呼ぶ
        repeat(3) {
            viewModel.cancelLastAnswer()
        }

        // Then: 全ての回答が削除される
        assertEquals(0, viewModel.quizState.value.userAnswers.size)

        // When: 再度回答する
        viewModel.appendDigit(3)
        viewModel.submitAnswer()

        // Then: 新しい回答が記録される
        assertEquals(1, viewModel.quizState.value.userAnswers.size)
        assertEquals(3, viewModel.quizState.value.userAnswers[0].answer)
    }

    @Test
    fun cancelLastAnswerで回答が空の場合は何も起こらない() {
        // Given: 回答が空のViewModel
        val viewModel = createViewModel(OperationType.Addition, Level.Easy)

        // When: cancelLastAnswerを呼ぶ
        viewModel.cancelLastAnswer()

        // Then: 回答は空のまま
        assertEquals(0, viewModel.quizState.value.userAnswers.size)
    }
}
