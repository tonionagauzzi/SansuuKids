package com.vitantonio.nagauzzi.sansuukids.logic.quiz

import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import com.vitantonio.nagauzzi.sansuukids.model.Question
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Addition
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Subtraction
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Multiplication
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Division
import com.vitantonio.nagauzzi.sansuukids.model.Quiz
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import kotlin.random.Random

private const val QUIZ_SIZE = 10

/**
 * クイズを生成するロジッククラス。
 *
 * 指定された計算モードと難易度レベルに基づいて、複数の問題から成るクイズを生成する。
 *
 * @param totalQuestions 生成する問題数
 */
internal class GenerateQuiz(private val totalQuestions: Int = QUIZ_SIZE) {
    /**
     * 指定されたモードとレベルに基づいてクイズを生成する。
     *
     * @param operationType 計算モード（足し算、引き算、掛け算、割り算、または全て）
     * @param level 難易度レベル（かんたん、ふつう、むずかしい）
     * @param quizRange カスタム出題範囲（全ての場合）
     * @return 生成された10問のクイズ
     */
    operator fun invoke(
        operationType: OperationType,
        level: Level,
        quizRange: QuizRange
    ): Quiz {
        val random = Random
        val questions = mutableSetOf<Question>() // 問題の重複を避けるためSetを使用
        while (questions.size < totalQuestions) {
            questions.add(generateQuestion(operationType, level, random, quizRange))
        }
        return Quiz(questions.toList(), operationType, level)
    }
}

/**
 * 演算タイプとレベルに応じた問題を1問生成する。
 *
 * @param operationType 演算タイプ
 * @param level 難易度レベル
 * @param random 乱数生成器
 * @param quizRange 出題範囲のリスト（演算タイプが全ての場合、決定した演算タイプと出題範囲のリストが噛み合わないことがあるので、デフォルトの出題範囲を強制適用する）
 * @return 生成された問題
 */
private fun generateQuestion(
    operationType: OperationType,
    level: Level,
    random: Random,
    quizRange: QuizRange
): Question {
    return when (operationType) {
        OperationType.Addition -> generateAddition(random, quizRange)
        OperationType.Subtraction -> generateSubtraction(random, quizRange)
        OperationType.Multiplication -> generateMultiplication(random, quizRange)
        OperationType.Division -> generateDivision(random, quizRange)
        OperationType.All -> {
            val randomMode = listOf(
                OperationType.Addition,
                OperationType.Subtraction,
                OperationType.Multiplication,
                OperationType.Division
            ).random(random)
            val defaultRange = QuizRange.Default(randomMode, level)
            generateQuestion(randomMode, level, random, defaultRange)
        }
    }
}

/**
 * 足し算の問題を生成する。
 *
 * @param random 乱数生成器
 * @param range 出題範囲
 * @return 足し算の問題
 */
private fun generateAddition(
    random: Random,
    range: QuizRange
): Addition {
    val left = random.nextInt(range.min, range.max + 1)
    val right = random.nextInt(range.min, range.max + 1)
    return Addition(left, right)
}

/**
 * 引き算の問題を生成する。
 *
 * 答えが負の数にならないように、大きい方を左辺、小さい方を右辺に配置する。
 *
 * @param random 乱数生成器
 * @param range 出題範囲
 * @return 引き算の問題
 */
private fun generateSubtraction(
    random: Random,
    range: QuizRange
): Subtraction {
    val a = random.nextInt(range.min, range.max + 1)
    val b = random.nextInt(range.min, range.max + 1)
    val left = maxOf(a, b)
    val right = minOf(a, b)
    return Subtraction(left, right)
}

/**
 * 掛け算の問題を生成する。
 *
 * @param random 乱数生成器
 * @param range 出題範囲
 * @return 掛け算の問題
 */
private fun generateMultiplication(
    random: Random,
    range: QuizRange
): Multiplication {
    val left = random.nextInt(range.min, range.max + 1)
    val right = random.nextInt(range.min, range.max + 1)
    return Multiplication(left, right)
}

/**
 * 割り算の問題を生成する。
 *
 * 必ず割り切れる問題を生成するため、先に除数と商を決定し、それらの積を被除数とする。
 *
 * @param random 乱数生成器
 * @param range 出題範囲
 * @return 割り算の問題
 */
private fun generateDivision(
    random: Random,
    range: QuizRange
): Division {
    val divisor = random.nextInt(range.min.coerceAtLeast(1), range.max + 1)
    val quotient = random.nextInt(range.min.coerceAtLeast(1), range.max + 1)
    val dividend = divisor * quotient
    return Division(dividend, divisor)
}
