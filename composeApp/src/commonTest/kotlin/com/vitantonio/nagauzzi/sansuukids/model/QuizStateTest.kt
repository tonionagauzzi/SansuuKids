package com.vitantonio.nagauzzi.sansuukids.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Addition

class QuizStateTest {
    private val quizSize = 10

    private fun createTestQuiz(): Quiz {
        val questions = (1..quizSize).map { index ->
            Addition(
                leftOperand = index,
                rightOperand = 1
            )
        }
        return Quiz(questions, Mode.Addition, Level.Easy)
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
    fun currentInputの初期値は空文字列() {
        // Given: テスト用クイズを作成する
        val quiz = createTestQuiz()

        // When: QuizStateを初期化する
        val state = QuizState(quiz)

        // Then: currentInputがnullである
        assertEquals(null, state.currentInput)
    }

    @Test
    fun userAnswersの初期値は空リスト() {
        // Given: テスト用クイズを作成する
        val quiz = createTestQuiz()

        // When: QuizStateを初期化する
        val state = QuizState(quiz)

        // Then: userAnswersが空リスト
        assertEquals(emptyList(), state.userAnswers)
    }

    @Test
    fun 全問回答するとisQuizCompleteがtrueになる() {
        // Given: 全問回答済みのQuizState
        val quiz = createTestQuiz()
        val userAnswers = (0 until quizSize).map { index ->
            UserAnswer(
                questionIndex = index,
                answer = index + 2,
                isCorrect = true
            )
        }

        // When: 全問回答済みの状態を作成する
        val state = QuizState(
            quiz = quiz,
            userAnswers = userAnswers
        )

        // Then: クイズが完了している
        assertTrue(state.isQuizComplete)
        assertEquals(quizSize, state.answeredCount)
    }

    @Test
    fun 途中までの回答ではisQuizCompleteがfalseのまま() {
        // Given: 途中まで回答済みのQuizState
        val quiz = createTestQuiz()
        val userAnswers = listOf(
            UserAnswer(questionIndex = 0, answer = 2, isCorrect = true),
            UserAnswer(questionIndex = 1, answer = 3, isCorrect = true)
        )

        // When: 途中まで回答済みの状態を作成する
        val state = QuizState(
            quiz = quiz,
            userAnswers = userAnswers
        )

        // Then: クイズは完了していない
        assertFalse(state.isQuizComplete)
        assertEquals(2, state.answeredCount)
    }

    @Test
    fun progressは現在の回答済み問題数に応じた値を返す() {
        // Given: テスト用クイズ
        val quiz = createTestQuiz()

        // When: 0問回答済み
        val state1 = QuizState(quiz, userAnswers = emptyList())

        // Then: 進捗は0.0（0/10）
        assertEquals(0.0f, state1.progress)

        // When: 1問回答済み
        val state2 = QuizState(
            quiz, userAnswers = listOf(
                UserAnswer(questionIndex = 0, answer = 2, isCorrect = true)
            )
        )

        // Then: 進捗は0.1（1/10）
        assertEquals(0.1f, state2.progress)
    }

    @Test
    fun answeredCountは回答した問題数を返す() {
        // Given: テスト用クイズ
        val quiz = createTestQuiz()

        // When: 初期状態
        val state1 = QuizState(quiz)

        // Then: 回答数は0
        assertEquals(0, state1.answeredCount)

        // When: 3問回答後
        val userAnswers = listOf(
            UserAnswer(questionIndex = 0, answer = 2, isCorrect = true),
            UserAnswer(questionIndex = 1, answer = 3, isCorrect = true),
            UserAnswer(questionIndex = 2, answer = 4, isCorrect = true)
        )
        val state2 = QuizState(
            quiz = quiz,
            userAnswers = userAnswers
        )

        // Then: 回答数は3
        assertEquals(3, state2.answeredCount)
    }

    @Test
    fun currentQuestionは回答済み問題数から次の問題を返す() {
        // Given: テスト用クイズ
        val quiz = createTestQuiz()

        // When: 3問回答済み
        val state = QuizState(
            quiz, userAnswers = listOf(
                UserAnswer(questionIndex = 0, answer = 2, isCorrect = true),
                UserAnswer(questionIndex = 1, answer = 3, isCorrect = false),
                UserAnswer(questionIndex = 2, answer = 4, isCorrect = true),
            )
        )

        // Then: 4問目の問題を返す
        assertEquals(quiz.questions[3], state.currentQuestion)
    }

    @Test
    fun 問題が存在しない場合currentQuestionはNoneを返す() {
        // Given: 問題が存在しないクイズ
        val quiz = Quiz(emptyList(), Mode.Addition, Level.Easy)

        // When: QuizStateを初期化する
        val state = QuizState(quiz)

        // Then: Question.Noneが返される
        assertIs<Question.None>(state.currentQuestion)
    }

    @Test
    fun correctCountは正解数を返す() {
        // Given: テスト用クイズ
        val quiz = createTestQuiz()
        val userAnswers = listOf(
            UserAnswer(questionIndex = 0, answer = 2, isCorrect = true),
            UserAnswer(questionIndex = 1, answer = 3, isCorrect = false),
            UserAnswer(questionIndex = 2, answer = 4, isCorrect = true)
        )

        // When: 回答を含むQuizStateを作成する
        val state = QuizState(quiz = quiz, userAnswers = userAnswers)

        // Then: 正解数は2
        assertEquals(2, state.correctCount)
    }
}
