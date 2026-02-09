package com.vitantonio.nagauzzi.sansuukids.logic

import com.vitantonio.nagauzzi.sansuukids.logic.quiz.GenerateQuiz
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Addition
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Subtraction
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Multiplication
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Division
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GenerateQuizTest {

    @Test
    fun 指定したモードとレベルのクイズを生成する() {
        // Given: 任意のモードとレベルを指定する
        val operationType = OperationType.Addition
        val level = Level.Easy
        val quizRange = QuizRange.Default(operationType, level)
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(operationType, level, quizRange)

        // Then: 指定したモードとレベルのクイズが生成される
        assertEquals(operationType, quiz.operationType)
        assertEquals(level, quiz.level)
    }

    @Test
    fun 足し算モードでは全問が足し算である() {
        // Given: 足し算モードを指定する
        val operationType = OperationType.Addition
        val level = Level.Easy
        val quizRange = QuizRange.Default(operationType, level)
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(operationType, level, quizRange)

        // Then: 全問が足し算である
        assertTrue(quiz.questions.all { it is Addition })
    }

    @Test
    fun 引き算モードで生成された問題の答えは0以上である() {
        // Given: 引き算モードを指定する
        val operationType = OperationType.Subtraction
        val level = Level.Normal
        val quizRange = QuizRange.Default(operationType, level)
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(operationType, level, quizRange)

        // Then: 全問の答えが0以上である
        assertTrue(quiz.questions.map { it as Subtraction }.all { it.correctAnswer >= 0 })
    }

    @Test
    fun 割り算モードで生成された問題は割り切れる() {
        // Given: 割り算モードを指定する
        val operationType = OperationType.Division
        val level = Level.Normal
        val quizRange = QuizRange.Default(operationType, level)
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(operationType, level, quizRange)

        // Then: 全問が割り切れる（余りなし）
        assertTrue(quiz.questions.all { question ->
            question is Division && question.dividend % question.divisor == 0 &&
                    question.dividend / question.divisor == question.correctAnswer
        })
    }

    // 足し算のレベル別テスト
    @Test
    fun 足し算の簡単レベルでは1から9の数が生成される() {
        // Given: 足し算モードと簡単レベルを指定する
        val operationType = OperationType.Addition
        val level = Level.Easy
        val quizRange = QuizRange.Default(operationType, level)
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(operationType, level, quizRange)

        // Then: 全問の左右オペランドが1〜9である
        assertTrue(quiz.questions.all { question ->
            question is Addition &&
                    question.leftOperand in 1..9 &&
                    question.rightOperand in 1..9
        })
    }

    @Test
    fun 足し算の普通レベルでは10から99の数が生成される() {
        // Given: 足し算モードと普通レベルを指定する
        val operationType = OperationType.Addition
        val level = Level.Normal
        val quizRange = QuizRange.Default(operationType, level)
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(operationType, level, quizRange)

        // Then: 全問の左右オペランドが10〜99である
        assertTrue(quiz.questions.all { question ->
            question is Addition &&
                    question.leftOperand in 10..99 &&
                    question.rightOperand in 10..99
        })
    }

    @Test
    fun 足し算の難しいレベルでは100から10000の数が生成される() {
        // Given: 足し算モードと難しいレベルを指定する
        val operationType = OperationType.Addition
        val level = Level.Difficult
        val quizRange = QuizRange.Default(operationType, level)
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(operationType, level, quizRange)

        // Then: 全問の左右オペランドが100〜10000である
        assertTrue(quiz.questions.all { question ->
            question is Addition &&
                    question.leftOperand in 100..10000 &&
                    question.rightOperand in 100..10000
        })
    }

    // 引き算のレベル別テスト
    @Test
    fun 引き算の簡単レベルでは1から9の数が生成される() {
        // Given: 引き算モードと簡単レベルを指定する
        val operationType = OperationType.Subtraction
        val level = Level.Easy
        val quizRange = QuizRange.Default(operationType, level)
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(operationType, level, quizRange)

        // Then: 全問の左右オペランドが1〜9である
        assertTrue(quiz.questions.all { question ->
            question is Subtraction &&
                    question.leftOperand in 1..9 &&
                    question.rightOperand in 1..9
        })
    }

    @Test
    fun 引き算の普通レベルでは10から99の数が生成される() {
        // Given: 引き算モードと普通レベルを指定する
        val operationType = OperationType.Subtraction
        val level = Level.Normal
        val quizRange = QuizRange.Default(operationType, level)
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(operationType, level, quizRange)

        // Then: 全問の左右オペランドが10〜99である
        assertTrue(quiz.questions.all { question ->
            question is Subtraction &&
                    question.leftOperand in 10..99 &&
                    question.rightOperand in 10..99
        })
    }

    @Test
    fun 引き算の難しいレベルでは100から10000の数が生成される() {
        // Given: 引き算モードと難しいレベルを指定する
        val operationType = OperationType.Subtraction
        val level = Level.Difficult
        val quizRange = QuizRange.Default(operationType, level)
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(operationType, level, quizRange)

        // Then: 全問の左右オペランドが100〜10000である
        assertTrue(quiz.questions.all { question ->
            question is Subtraction &&
                    question.leftOperand in 100..10000 &&
                    question.rightOperand in 100..10000
        })
    }

    // 掛け算のレベル別テスト
    @Test
    fun 掛け算の簡単レベルでは1から9の数が生成される() {
        // Given: 掛け算モードと簡単レベルを指定する
        val operationType = OperationType.Multiplication
        val level = Level.Easy
        val quizRange = QuizRange.Default(operationType, level)
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(operationType, level, quizRange)

        // Then: 全問の左右オペランドが1〜9である
        assertTrue(quiz.questions.all { question ->
            question is Multiplication &&
                    question.leftOperand in 1..9 &&
                    question.rightOperand in 1..9
        })
    }

    @Test
    fun 掛け算の普通レベルでは6から19の数が生成される() {
        // Given: 掛け算モードと普通レベルを指定する
        val operationType = OperationType.Multiplication
        val level = Level.Normal
        val quizRange = QuizRange.Default(operationType, level)
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(operationType, level, quizRange)

        // Then: 全問の左右オペランドが6〜19である
        assertTrue(quiz.questions.all { question ->
            question is Multiplication &&
                    question.leftOperand in 6..19 &&
                    question.rightOperand in 6..19
        })
    }

    @Test
    fun 掛け算の難しいレベルでは10から100の数が生成される() {
        // Given: 掛け算モードと難しいレベルを指定する
        val operationType = OperationType.Multiplication
        val level = Level.Difficult
        val quizRange = QuizRange.Default(operationType, level)
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(operationType, level, quizRange)

        // Then: 全問の左右オペランドが10〜100である
        assertTrue(quiz.questions.all { question ->
            question is Multiplication &&
                    question.leftOperand in 10..100 &&
                    question.rightOperand in 10..100
        })
    }

    // 割り算のレベル別テスト
    @Test
    fun 割り算の簡単レベルでは1から9の数が生成される() {
        // Given: 割り算モードと簡単レベルを指定する
        val operationType = OperationType.Division
        val level = Level.Easy
        val quizRange = QuizRange.Default(operationType, level)
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(operationType, level, quizRange)

        // Then: 全問の除数と商が1〜9である
        assertTrue(quiz.questions.all { question ->
            question is Division &&
                    question.divisor in 1..9 &&
                    question.correctAnswer in 1..9
        })
    }

    @Test
    fun 割り算の普通レベルでは6から19の数が生成される() {
        // Given: 割り算モードと普通レベルを指定する
        val operationType = OperationType.Division
        val level = Level.Normal
        val quizRange = QuizRange.Default(operationType, level)
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(operationType, level, quizRange)

        // Then: 全問の除数と商が6〜19である
        assertTrue(quiz.questions.all { question ->
            question is Division &&
                    question.divisor in 6..19 &&
                    question.correctAnswer in 6..19
        })
    }

    @Test
    fun 割り算の難しいレベルでは10から100の数が生成される() {
        // Given: 割り算モードと難しいレベルを指定する
        val operationType = OperationType.Division
        val level = Level.Difficult
        val quizRange = QuizRange.Default(operationType, level)
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(operationType, level, quizRange)

        // Then: 全問の除数と商が10〜100である
        assertTrue(quiz.questions.all { question ->
            question is Division &&
                    question.divisor in 10..100 &&
                    question.correctAnswer in 10..100
        })
    }

    @Test
    fun すべてモードでは4種類の演算子が生成される可能性がある() {
        // Given: すべてモードを指定する
        val operationType = OperationType.All
        val level = Level.Easy
        val quizRange = QuizRange.Default(operationType, level)
        val generateQuiz = GenerateQuiz()

        // When: 10回クイズを生成して（合計100問）、演算子を収集する（1回分10問では不十分な場合があるため）
        val allQuestionTypes = (1..10).flatMap {
            generateQuiz(operationType, level, quizRange).questions.map { q -> q::class }
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
        // Given: 足し算モードを指定する
        val operationType = OperationType.Addition
        val level = Level.Easy
        val quizRange = QuizRange.Default(operationType, level)
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(operationType, level, quizRange)

        // Then: 全問の正解が左右のオペランドの和である
        assertTrue(quiz.questions.all { question ->
            question is Addition &&
                    question.leftOperand + question.rightOperand == question.correctAnswer
        })
    }

    @Test
    fun 引き算の正解は左から右を引いた値である() {
        // Given: 引き算モードを指定する
        val operationType = OperationType.Subtraction
        val level = Level.Easy
        val quizRange = QuizRange.Default(operationType, level)
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(operationType, level, quizRange)

        // Then: 全問の正解が左から右を引いた値である
        assertTrue(quiz.questions.all { question ->
            question is Subtraction &&
                    question.leftOperand - question.rightOperand == question.correctAnswer
        })
    }

    @Test
    fun 掛け算の正解は左右のオペランドの積である() {
        // Given: 掛け算モードを指定する
        val operationType = OperationType.Multiplication
        val level = Level.Easy
        val quizRange = QuizRange.Default(operationType, level)
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(operationType, level, quizRange)

        // Then: 全問の正解が左右のオペランドの積である
        assertTrue(quiz.questions.all { question ->
            question is Multiplication &&
                    question.leftOperand * question.rightOperand == question.correctAnswer
        })
    }

    @Test
    fun 割り算の正解は左を右で割った値である() {
        // Given: 割り算モードを指定する
        val operationType = OperationType.Division
        val level = Level.Easy
        val quizRange = QuizRange.Default(operationType, level)
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(operationType, level, quizRange)

        // Then: 全問の正解が左を右で割った値である
        assertTrue(quiz.questions.all { question ->
            question is Division && question.dividend / question.divisor == question.correctAnswer
        })
    }

    // カスタム範囲のテスト
    @Test
    fun カスタム範囲で足し算の出題範囲を変更できる() {
        // Given: 足し算の簡単レベルでカスタム範囲を1〜10に設定する
        val quizRange = QuizRange.Custom(OperationType.Addition, Level.Easy, min = 1, max = 10)
        val generateQuiz = GenerateQuiz()

        // When: カスタム範囲でクイズを生成する
        val quiz = generateQuiz(OperationType.Addition, Level.Easy, quizRange)

        // Then: 全問の左右オペランドが1〜10である
        assertTrue(quiz.questions.all { question ->
            question is Addition &&
                    question.leftOperand in 1..10 &&
                    question.rightOperand in 1..10
        })
    }

    @Test
    fun カスタム範囲で引き算の出題範囲を変更できる() {
        // Given: 引き算の普通レベルでカスタム範囲を5〜50に設定する
        val quizRange = QuizRange.Custom(OperationType.Subtraction, Level.Normal, min = 5, max = 50)
        val generateQuiz = GenerateQuiz()

        // When: カスタム範囲でクイズを生成する
        val quiz = generateQuiz(OperationType.Subtraction, Level.Normal, quizRange)

        // Then: 全問の左右オペランドが5〜50である
        assertTrue(quiz.questions.all { question ->
            question is Subtraction &&
                    question.leftOperand in 5..50 &&
                    question.rightOperand in 5..50
        })
    }

    @Test
    fun カスタム範囲で掛け算の出題範囲を変更できる() {
        // Given: 掛け算の簡単レベルでカスタム範囲を1〜5に設定する
        val quizRange = QuizRange.Custom(OperationType.Multiplication, Level.Easy, min = 1, max = 5)
        val generateQuiz = GenerateQuiz()

        // When: カスタム範囲でクイズを生成する
        val quiz = generateQuiz(OperationType.Multiplication, Level.Easy, quizRange)

        // Then: 全問の左右オペランドが1〜5である
        assertTrue(quiz.questions.all { question ->
            question is Multiplication &&
                    question.leftOperand in 1..5 &&
                    question.rightOperand in 1..5
        })
    }

    @Test
    fun カスタム範囲がデフォルトの場合はデフォルト範囲で出題される() {
        // Given: デフォルト範囲
        val quizRange = QuizRange.Default(OperationType.Addition, Level.Easy)
        val generateQuiz = GenerateQuiz()

        // When: クイズを生成する
        val quiz = generateQuiz(OperationType.Addition, Level.Easy, quizRange)

        // Then: デフォルト範囲（1〜9）で生成される
        assertTrue(quiz.questions.all { question ->
            question is Addition &&
                    question.leftOperand in 1..9 &&
                    question.rightOperand in 1..9
        })
    }

    @Test
    fun 生成されたクイズに同一問題の重複がない() {
        // Given: 全モードとレベルの組み合わせ
        val operationTypes = OperationType.entries
        val levels = Level.entries
        val generateQuiz = GenerateQuiz()

        // When/Then: 各組み合わせでクイズを生成し、重複がないことを確認
        operationTypes.forEach { operationType ->
            levels.forEach { level ->
                val quizRange = QuizRange.Default(operationType, level)
                val quiz = generateQuiz(operationType, level, quizRange)
                val distinctQuestions = quiz.questions.distinct()
                assertEquals(
                    quiz.questions.size,
                    distinctQuestions.size,
                    "OperationType: $operationType, Level: $level で重複問題が発生"
                )
            }
        }
    }
}
