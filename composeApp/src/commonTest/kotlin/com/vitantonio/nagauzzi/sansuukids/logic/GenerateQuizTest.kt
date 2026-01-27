package com.vitantonio.nagauzzi.sansuukids.logic

import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Addition
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Subtraction
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Multiplication
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Division
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
        assertTrue(quiz.questions.all { it is Addition })
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
        assertTrue(quiz.questions.map { it as Subtraction }.all { it.correctAnswer >= 0 })
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
            question is Division && question.dividend % question.divisor == 0 &&
                    question.dividend / question.divisor == question.correctAnswer
        })
    }

    // 足し算のレベル別テスト
    @Test
    fun 足し算のEASYレベルでは1から5の数が生成される() {
        // Given: EASYレベルとADDITIONモードを指定する
        val mode = Mode.ADDITION
        val level = Level.EASY
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 全問の左右オペランドが1〜5である（答えが2桁にならないように）
        assertTrue(quiz.questions.all { question ->
            question is Addition &&
                    question.leftOperand in 1..5 &&
                    question.rightOperand in 1..5
        })
    }

    @Test
    fun 足し算のNORMALレベルでは11から50の数が生成される() {
        // Given: NORMALレベルとADDITIONモードを指定する
        val mode = Mode.ADDITION
        val level = Level.NORMAL
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 全問の左右オペランドが11〜50である（答えが3桁にならないように）
        assertTrue(quiz.questions.all { question ->
            question is Addition &&
                    question.leftOperand in 11..50 &&
                    question.rightOperand in 11..50
        })
    }

    @Test
    fun 足し算のDIFFICULTレベルでは101から9999の数が生成される() {
        // Given: DIFFICULTレベルとADDITIONモードを指定する
        val mode = Mode.ADDITION
        val level = Level.DIFFICULT
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 全問の左右オペランドが101〜9999である
        assertTrue(quiz.questions.all { question ->
            question is Addition &&
                    question.leftOperand in 101..9999 &&
                    question.rightOperand in 101..9999
        })
    }

    // 引き算のレベル別テスト
    @Test
    fun 引き算のEASYレベルでは1から9の数が生成される() {
        // Given: EASYレベルとSUBTRACTIONモードを指定する
        val mode = Mode.SUBTRACTION
        val level = Level.EASY
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 全問の左右オペランドが1〜9である
        assertTrue(quiz.questions.all { question ->
            question is Subtraction &&
                    question.leftOperand in 1..9 &&
                    question.rightOperand in 1..9
        })
    }

    @Test
    fun 引き算のNORMALレベルでは11から99の数が生成される() {
        // Given: NORMALレベルとSUBTRACTIONモードを指定する
        val mode = Mode.SUBTRACTION
        val level = Level.NORMAL
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 全問の左右オペランドが11〜99である
        assertTrue(quiz.questions.all { question ->
            question is Subtraction &&
                    question.leftOperand in 11..99 &&
                    question.rightOperand in 11..99
        })
    }

    @Test
    fun 引き算のDIFFICULTレベルでは101から9999の数が生成される() {
        // Given: DIFFICULTレベルとSUBTRACTIONモードを指定する
        val mode = Mode.SUBTRACTION
        val level = Level.DIFFICULT
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 全問の左右オペランドが101〜9999である
        assertTrue(quiz.questions.all { question ->
            question is Subtraction &&
                    question.leftOperand in 101..9999 &&
                    question.rightOperand in 101..9999
        })
    }

    // 掛け算のレベル別テスト
    @Test
    fun 掛け算のEASYレベルでは1から9の数が生成される() {
        // Given: EASYレベルとMULTIPLICATIONモードを指定する
        val mode = Mode.MULTIPLICATION
        val level = Level.EASY
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 全問の左右オペランドが1〜9である
        assertTrue(quiz.questions.all { question ->
            question is Multiplication &&
                    question.leftOperand in 1..9 &&
                    question.rightOperand in 1..9
        })
    }

    @Test
    fun 掛け算のNORMALレベルでは6から19の数が生成される() {
        // Given: NORMALレベルとMULTIPLICATIONモードを指定する
        val mode = Mode.MULTIPLICATION
        val level = Level.NORMAL
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 全問の左右オペランドが6〜19である
        assertTrue(quiz.questions.all { question ->
            question is Multiplication &&
                    question.leftOperand in 6..19 &&
                    question.rightOperand in 6..19
        })
    }

    @Test
    fun 掛け算のDIFFICULTレベルでは11から99の数が生成される() {
        // Given: DIFFICULTレベルとMULTIPLICATIONモードを指定する
        val mode = Mode.MULTIPLICATION
        val level = Level.DIFFICULT
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 全問の左右オペランドが11〜99である
        assertTrue(quiz.questions.all { question ->
            question is Multiplication &&
                    question.leftOperand in 11..99 &&
                    question.rightOperand in 11..99
        })
    }

    // 割り算のレベル別テスト
    @Test
    fun 割り算のEASYレベルでは1から9の数が生成される() {
        // Given: EASYレベルとDIVISIONモードを指定する
        val mode = Mode.DIVISION
        val level = Level.EASY
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 全問の除数と商が1〜9である
        assertTrue(quiz.questions.all { question ->
            question is Division &&
                    question.divisor in 1..9 &&
                    question.correctAnswer in 1..9
        })
    }

    @Test
    fun 割り算のNORMALレベルでは6から19の数が生成される() {
        // Given: NORMALレベルとDIVISIONモードを指定する
        val mode = Mode.DIVISION
        val level = Level.NORMAL
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 全問の除数と商が6〜19である
        assertTrue(quiz.questions.all { question ->
            question is Division &&
                    question.divisor in 6..19 &&
                    question.correctAnswer in 6..19
        })
    }

    @Test
    fun 割り算のDIFFICULTレベルでは11から99の数が生成される() {
        // Given: DIFFICULTレベルとDIVISIONモードを指定する
        val mode = Mode.DIVISION
        val level = Level.DIFFICULT
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(mode, level)

        // Then: 全問の除数と商が11〜99である
        assertTrue(quiz.questions.all { question ->
            question is Division &&
                    question.divisor in 11..99 &&
                    question.correctAnswer in 11..99
        })
    }

    @Test
    fun ALLモードでは4種類の演算子が生成される可能性がある() {
        // Given: ALLモードを指定する
        val mode = Mode.ALL
        val level = Level.EASY
        val generateQuiz = GenerateQuiz()

        // When: 10回クイズを生成して（合計100問）、演算子を収集する（1回分10問では不十分な場合があるため）
        val allQuestionTypes = (1..10).flatMap {
            generateQuiz(mode, level).questions.map { q -> q::class }
        }.toSet()

        // Then: 4種類の演算子が含まれる
        val expectedTypes = setOf(
            Addition::class,
            Subtraction::class,
            Multiplication::class,
            Division::class
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
            question is Addition &&
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
            question is Subtraction &&
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
            question is Multiplication &&
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
            question is Division && question.dividend / question.divisor == question.correctAnswer
        })
    }

    @Test
    fun 生成されたクイズに同一問題の重複がない() {
        // Given: 全モードとレベルの組み合わせ
        val modes = listOf(Mode.ADDITION, Mode.SUBTRACTION, Mode.MULTIPLICATION, Mode.DIVISION, Mode.ALL)
        val levels = listOf(Level.EASY, Level.NORMAL, Level.DIFFICULT)
        val generateQuiz = GenerateQuiz()

        // When/Then: 各組み合わせでクイズを生成し、重複がないことを確認
        modes.forEach { mode ->
            levels.forEach { level ->
                val quiz = generateQuiz(mode, level)
                val distinctQuestions = quiz.questions.distinct()
                assertEquals(
                    quiz.questions.size,
                    distinctQuestions.size,
                    "Mode: $mode, Level: $level で重複問題が発生"
                )
            }
        }
    }
}
