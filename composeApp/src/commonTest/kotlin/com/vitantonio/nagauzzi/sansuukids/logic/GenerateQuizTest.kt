package com.vitantonio.nagauzzi.sansuukids.logic

import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.model.Question
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GenerateQuizTest {

    @Test
    fun 指定したモードとレベルのクイズを生成する() {
        // Given: 任意のモードとレベルを指定する
        val mode = Mode.ADDITION
        val level = Level.EASY
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 指定したモードとレベルのクイズが生成される
        assertEquals(mode, quiz.mode)
        assertEquals(level, quiz.level)
    }

    @Test
    fun ADDITIONモードでは全問が足し算である() {
        // Given: ADDITIONモードを指定する
        val mode = Mode.ADDITION
        val level = Level.EASY
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 全問が足し算である
        assertTrue(quiz.questions.all { it is Question.Addition })
    }

    @Test
    fun SUBTRACTIONモードで生成された問題の答えは0以上である() {
        // Given: SUBTRACTIONモードを指定する
        val mode = Mode.SUBTRACTION
        val level = Level.NORMAL
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 全問の答えが0以上である
        assertTrue(quiz.questions.all { it.correctAnswer >= 0 })
    }

    @Test
    fun DIVISIONモードで生成された問題は割り切れる() {
        // Given: DIVISIONモードを指定する
        val mode = Mode.DIVISION
        val level = Level.NORMAL
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 全問が割り切れる（余りなし）
        assertTrue(quiz.questions.all { question ->
            question is Question.Division &&
                    question.dividend % question.divisor == 0 &&
                    question.dividend / question.divisor == question.correctAnswer
        })
    }

    @Test
    fun EASYレベルでは1桁の数が生成される() {
        // Given: EASYレベルとADDITIONモードを指定する
        val mode = Mode.ADDITION
        val level = Level.EASY
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 全問の左右オペランドが1〜9である
        assertTrue(quiz.questions.all { question ->
            question is Question.Addition &&
                    question.leftOperand in 1..9 &&
                    question.rightOperand in 1..9
        })
    }

    @Test
    fun NORMALレベルでは1から99の数が生成される() {
        // Given: NORMALレベルとADDITIONモードを指定する
        val mode = Mode.ADDITION
        val level = Level.NORMAL
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 全問の左右オペランドが1〜99である
        assertTrue(quiz.questions.all { question ->
            question is Question.Addition &&
                    question.leftOperand in 1..99 &&
                    question.rightOperand in 1..99
        })
    }

    @Test
    fun DIFFICULTレベルでは3桁以上の数が含まれる() {
        // Given: DIFFICULTレベルとADDITIONモードを指定する
        val mode = Mode.ADDITION
        val level = Level.DIFFICULT
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 全問の左右オペランドが100〜9999である
        assertTrue(quiz.questions.all { question ->
            question is Question.Addition &&
                    question.leftOperand in 100..9999 &&
                    question.rightOperand in 100..9999
        })
    }

    @Test
    fun ALLモードでは4種類の演算子が生成される可能性がある() {
        // Given: ALLモードを指定する
        val mode = Mode.ALL
        val level = Level.EASY
        val generateQuiz = GenerateQuiz()

        // When: 100問のクイズを生成して演算子を収集する（10問では不十分な場合があるため）
        val allQuestionTypes = (1..10).flatMap {
            generateQuiz(mode, level).questions.map { q -> q::class }
        }.toSet()

        // Then: 4種類の演算子が含まれる
        val expectedTypes = setOf(
            Question.Addition::class,
            Question.Subtraction::class,
            Question.Multiplication::class,
            Question.Division::class
        )
        assertEquals(expectedTypes, allQuestionTypes)
    }

    @Test
    fun 足し算の正解は左右のオペランドの和である() {
        // Given: ADDITIONモードを指定する
        val mode = Mode.ADDITION
        val level = Level.EASY
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 全問の正解が左右のオペランドの和である
        assertTrue(quiz.questions.all { question ->
            question is Question.Addition &&
                    question.leftOperand + question.rightOperand == question.correctAnswer
        })
    }

    @Test
    fun 引き算の正解は左から右を引いた値である() {
        // Given: SUBTRACTIONモードを指定する
        val mode = Mode.SUBTRACTION
        val level = Level.EASY
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 全問の正解が左から右を引いた値である
        assertTrue(quiz.questions.all { question ->
            question is Question.Subtraction &&
                    question.leftOperand - question.rightOperand == question.correctAnswer
        })
    }

    @Test
    fun 掛け算の正解は左右のオペランドの積である() {
        // Given: MULTIPLICATIONモードを指定する
        val mode = Mode.MULTIPLICATION
        val level = Level.EASY
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 全問の正解が左右のオペランドの積である
        assertTrue(quiz.questions.all { question ->
            question is Question.Multiplication &&
                    question.leftOperand * question.rightOperand == question.correctAnswer
        })
    }

    @Test
    fun 割り算の正解は左を右で割った値である() {
        // Given: DIVISIONモードを指定する
        val mode = Mode.DIVISION
        val level = Level.EASY
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 全問の正解が左を右で割った値である
        assertTrue(quiz.questions.all { question ->
            question is Question.Division &&
                    question.dividend / question.divisor == question.correctAnswer
        })
    }
}
