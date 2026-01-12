package com.vitantonio.nagauzzi.sansuukids.logic

import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.model.Operator
import com.vitantonio.nagauzzi.sansuukids.model.Question
import com.vitantonio.nagauzzi.sansuukids.model.Quiz
import kotlin.random.Random

private const val QUIZ_SIZE = 10

internal class GenerateQuiz {
    operator fun invoke(mode: Mode, level: Level): Quiz {
        val random = Random
        val questions = (1..QUIZ_SIZE).map {
            generateQuestion(mode, level, random)
        }
        return Quiz(questions, mode, level)
    }
}

private fun generateQuestion(mode: Mode, level: Level, random: Random): Question {
    val operator = when (mode) {
        Mode.ADDITION -> Operator.ADDITION
        Mode.SUBTRACTION -> Operator.SUBTRACTION
        Mode.MULTIPLICATION -> Operator.MULTIPLICATION
        Mode.DIVISION -> Operator.DIVISION
        Mode.ALL -> Operator.entries.random(random)
    }

    return when (operator) {
        Operator.ADDITION -> generateAddition(level, random)
        Operator.SUBTRACTION -> generateSubtraction(level, random)
        Operator.MULTIPLICATION -> generateMultiplication(level, random)
        Operator.DIVISION -> generateDivision(level, random)
    }
}

private fun generateAddition(level: Level, random: Random): Question {
    val (min, max) = getRangeForLevel(level)
    val left = random.nextInt(min, max + 1)
    val right = random.nextInt(min, max + 1)
    return Question(left, right, Operator.ADDITION, left + right)
}

private fun generateSubtraction(level: Level, random: Random): Question {
    val (min, max) = getRangeForLevel(level)
    val a = random.nextInt(min, max + 1)
    val b = random.nextInt(min, max + 1)
    val left = maxOf(a, b)
    val right = minOf(a, b)
    return Question(left, right, Operator.SUBTRACTION, left - right)
}

private fun generateMultiplication(level: Level, random: Random): Question {
    val (min, max) = getMultiplicationRangeForLevel(level)
    val left = random.nextInt(min, max + 1)
    val right = random.nextInt(min, max + 1)
    return Question(left, right, Operator.MULTIPLICATION, left * right)
}

private fun generateDivision(level: Level, random: Random): Question {
    val (min, max) = getMultiplicationRangeForLevel(level)
    val divisor = random.nextInt(min.coerceAtLeast(1), max + 1)
    val quotient = random.nextInt(min.coerceAtLeast(1), max + 1)
    val dividend = divisor * quotient
    return Question(dividend, divisor, Operator.DIVISION, quotient)
}

private fun getRangeForLevel(level: Level): Pair<Int, Int> = when (level) {
    Level.EASY -> 1 to 9
    Level.NORMAL -> 1 to 99
    Level.DIFFICULT -> 100 to 9999
}

// 掛け算は結果が大きくなりやすいため、足し算・引き算とは異なる範囲を設定
// 割り算は掛け算と難易度を合わせるために掛け算の範囲と合わせる
// EASY: 九九の範囲(1〜9)、NORMAL: 19の段まで(1〜19)、DIFFICULT: 二桁同士の計算(1〜99)
private fun getMultiplicationRangeForLevel(level: Level): Pair<Int, Int> = when (level) {
    Level.EASY -> 1 to 9
    Level.NORMAL -> 1 to 19
    Level.DIFFICULT -> 1 to 99
}
