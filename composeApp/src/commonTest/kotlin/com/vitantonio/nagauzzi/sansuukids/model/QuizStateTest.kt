package com.vitantonio.nagauzzi.sansuukids.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class QuizStateTest {

    private fun createTestQuiz(): Quiz {
        val questions = (1..Quiz.QUIZ_SIZE).map { index ->
            Question(
                leftOperand = index,
                rightOperand = 1,
                operator = Operator.ADDITION,
                correctAnswer = index + 1
            )
        }
        return Quiz(questions, Mode.ADDITION, Level.EASY)
    }

    @Test
    fun 初期状態では最初の問題が表示される() {
        // Given: テスト用クイズを作成する
        val quiz = createTestQuiz()

        // When: QuizStateを初期化する
        val state = QuizState(quiz)

        // Then: currentQuestionIndexが0で最初の問題が表示される
        assertEquals(0, state.currentQuestionIndex)
        assertEquals(quiz.questions[0], state.currentQuestion)
    }

    @Test
    fun appendDigitで数字を入力できる() {
        // Given: QuizStateを初期化する
        val state = QuizState(createTestQuiz())

        // When: 数字を入力する
        state.appendDigit(5)

        // Then: 入力が反映される
        assertEquals("5", state.currentInput)
    }

    @Test
    fun appendDigitで複数桁の数字を入力できる() {
        // Given: QuizStateを初期化する
        val state = QuizState(createTestQuiz())

        // When: 複数の数字を入力する
        state.appendDigit(1)
        state.appendDigit(2)
        state.appendDigit(3)

        // Then: 入力が連結される
        assertEquals("123", state.currentInput)
    }

    @Test
    fun deleteLastDigitで最後の数字を削除できる() {
        // Given: 数字を入力したQuizState
        val state = QuizState(createTestQuiz())
        state.appendDigit(1)
        state.appendDigit(2)

        // When: 最後の数字を削除する
        state.deleteLastDigit()

        // Then: 最後の数字が削除される
        assertEquals("1", state.currentInput)
    }

    @Test
    fun deleteLastDigitで空の入力には何も起こらない() {
        // Given: 空の入力のQuizState
        val state = QuizState(createTestQuiz())

        // When: 削除を試みる
        state.deleteLastDigit()

        // Then: 入力は空のまま
        assertEquals("", state.currentInput)
    }

    @Test
    fun `5桁以上は入力できない`() {
        // Given: QuizStateを初期化する
        val state = QuizState(createTestQuiz())

        // When: 6桁の数字を入力しようとする
        repeat(6) { state.appendDigit(1) }

        // Then: 5桁までしか入力されない
        assertEquals("11111", state.currentInput)
    }

    @Test
    fun submitAnswerで正解判定が行われる_正解の場合() {
        // Given: 最初の問題（1+1=2）のQuizState
        val state = QuizState(createTestQuiz())

        // When: 正解を入力して提出する
        state.appendDigit(2)
        val isCorrect = state.submitAnswer()

        // Then: 正解と判定される
        assertTrue(isCorrect)
        assertEquals(1, state.userAnswers.size)
        assertTrue(state.userAnswers[0].isCorrect)
    }

    @Test
    fun submitAnswerで正解判定が行われる_不正解の場合() {
        // Given: 最初の問題（1+1=2）のQuizState
        val state = QuizState(createTestQuiz())

        // When: 不正解を入力して提出する
        state.appendDigit(9)
        val isCorrect = state.submitAnswer()

        // Then: 不正解と判定される
        assertFalse(isCorrect)
        assertEquals(1, state.userAnswers.size)
        assertFalse(state.userAnswers[0].isCorrect)
    }

    @Test
    fun submitAnswerで次の問題に進む() {
        // Given: QuizStateを初期化する
        val state = QuizState(createTestQuiz())

        // When: 回答を提出する
        state.appendDigit(2)
        state.submitAnswer()

        // Then: 次の問題に進む
        assertEquals(1, state.currentQuestionIndex)
    }

    @Test
    fun submitAnswer後に入力がクリアされる() {
        // Given: 入力があるQuizState
        val state = QuizState(createTestQuiz())
        state.appendDigit(2)

        // When: 回答を提出する
        state.submitAnswer()

        // Then: 入力がクリアされる
        assertEquals("", state.currentInput)
    }

    @Test
    fun `10問回答するとisQuizCompleteがtrueになる`() {
        // Given: QuizStateを初期化する
        val state = QuizState(createTestQuiz())

        // When: 10問全て回答する
        repeat(Quiz.QUIZ_SIZE) { index ->
            state.appendDigit((index + 2) % 10)
            state.submitAnswer()
        }

        // Then: クイズが完了する
        assertTrue(state.isQuizComplete)
        assertEquals(Quiz.QUIZ_SIZE, state.answeredCount)
    }

    @Test
    fun toResultで正しいQuizResultが生成される() {
        // Given: いくつか回答したQuizState
        val state = QuizState(createTestQuiz())
        state.appendDigit(2)
        state.submitAnswer() // 正解
        state.appendDigit(9)
        state.submitAnswer() // 不正解

        // When: 結果を生成する
        val result = state.toResult()

        // Then: 正しいQuizResultが生成される
        assertEquals(2, result.userAnswers.size)
        assertEquals(1, result.correctCount)
    }

    @Test
    fun progressは現在の問題番号に応じた値を返す() {
        // Given: QuizStateを初期化する
        val state = QuizState(createTestQuiz())

        // When: 最初の問題
        // Then: 進捗は0.1（1/10）
        assertEquals(0.1f, state.progress)

        // When: 1問回答後
        state.appendDigit(2)
        state.submitAnswer()

        // Then: 進捗は0.2（2/10）
        assertEquals(0.2f, state.progress)
    }

    @Test
    fun answeredCountは回答した問題数を返す() {
        // Given: QuizStateを初期化する
        val state = QuizState(createTestQuiz())

        // When: 初期状態
        // Then: 回答数は0
        assertEquals(0, state.answeredCount)

        // When: 3問回答後
        repeat(3) {
            state.appendDigit(1)
            state.submitAnswer()
        }

        // Then: 回答数は3
        assertEquals(3, state.answeredCount)
    }
}
