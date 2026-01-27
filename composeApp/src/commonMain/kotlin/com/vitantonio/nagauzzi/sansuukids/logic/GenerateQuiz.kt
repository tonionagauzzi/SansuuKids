package com.vitantonio.nagauzzi.sansuukids.logic

import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.model.Question
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Addition
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Subtraction
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Multiplication
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Division
import com.vitantonio.nagauzzi.sansuukids.model.Quiz
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
     * @param mode 計算モード（足し算、引き算、掛け算、割り算、または全て）
     * @param level 難易度レベル（かんたん、ふつう、むずかしい）
     * @return 生成された10問のクイズ
     */
    operator fun invoke(mode: Mode, level: Level): Quiz {
        val random = Random
        val questions = mutableSetOf<Question>() // 問題の重複を避けるためSetを使用
        while (questions.size < totalQuestions) {
            questions.add(generateQuestion(mode, level, random))
        }
        return Quiz(questions.toList(), mode, level)
    }
}

/**
 * モードとレベルに応じた問題を1問生成する。
 *
 * @param mode 計算モード
 * @param level 難易度レベル
 * @param random 乱数生成器
 * @return 生成された問題
 */
private fun generateQuestion(mode: Mode, level: Level, random: Random): Question {
    return when (mode) {
        Mode.ADDITION -> generateAddition(level, random)
        Mode.SUBTRACTION -> generateSubtraction(level, random)
        Mode.MULTIPLICATION -> generateMultiplication(level, random)
        Mode.DIVISION -> generateDivision(level, random)
        Mode.ALL -> {
            val randomMode = listOf(
                Mode.ADDITION,
                Mode.SUBTRACTION,
                Mode.MULTIPLICATION,
                Mode.DIVISION
            ).random(random)
            generateQuestion(randomMode, level, random)
        }
    }
}

/**
 * 足し算の問題を生成する。
 *
 * @param level 難易度レベル
 * @param random 乱数生成器
 * @return 足し算の問題
 */
private fun generateAddition(level: Level, random: Random): Addition {
    val (min, max) = getAdditionRangeForLevel(level)
    val left = random.nextInt(min, max + 1)
    val right = random.nextInt(min, max + 1)
    return Addition(left, right)
}

/**
 * 引き算の問題を生成する。
 *
 * 答えが負の数にならないように、大きい方を左辺、小さい方を右辺に配置する。
 *
 * @param level 難易度レベル
 * @param random 乱数生成器
 * @return 引き算の問題
 */
private fun generateSubtraction(level: Level, random: Random): Subtraction {
    val (min, max) = getSubtractionRangeForLevel(level)
    val a = random.nextInt(min, max + 1)
    val b = random.nextInt(min, max + 1)
    val left = maxOf(a, b)
    val right = minOf(a, b)
    return Subtraction(left, right)
}

/**
 * 掛け算の問題を生成する。
 *
 * @param level 難易度レベル
 * @param random 乱数生成器
 * @return 掛け算の問題
 */
private fun generateMultiplication(level: Level, random: Random): Multiplication {
    val (min, max) = getMultiplicationRangeForLevel(level)
    val left = random.nextInt(min, max + 1)
    val right = random.nextInt(min, max + 1)
    return Multiplication(left, right)
}

/**
 * 割り算の問題を生成する。
 *
 * 必ず割り切れる問題を生成するため、先に除数と商を決定し、それらの積を被除数とする。
 *
 * @param level 難易度レベル
 * @param random 乱数生成器
 * @return 割り算の問題
 */
private fun generateDivision(level: Level, random: Random): Division {
    val (min, max) = getMultiplicationRangeForLevel(level)
    val divisor = random.nextInt(min.coerceAtLeast(1), max + 1)
    val quotient = random.nextInt(min.coerceAtLeast(1), max + 1)
    val dividend = divisor * quotient
    return Division(dividend, divisor)
}

/**
 * 足し算用の数値範囲を取得する。
 *
 * 足し算は答えが2桁や3桁にならないように範囲を調整している。
 * - EASY: 答えが最大10まで（1〜5）
 * - NORMAL: 答えが最大100まで（1〜50）
 * - DIFFICULT: 3桁以上の計算（100〜9999）
 *
 * @param level 難易度レベル
 * @return 最小値と最大値のペア
 */
private fun getAdditionRangeForLevel(level: Level): Pair<Int, Int> = when (level) {
    Level.EASY -> 1 to 5
    Level.NORMAL -> 11 to 50
    Level.DIFFICULT -> 101 to 9999
}

/**
 * 引き算用の数値範囲を取得する。
 *
 * - EASY: 1桁同士の計算（1〜9）
 * - NORMAL: 1〜2桁同士の計算（1〜99）
 * - DIFFICULT: 3桁以上の計算（100〜9999）
 *
 * @param level 難易度レベル
 * @return 最小値と最大値のペア
 */
private fun getSubtractionRangeForLevel(level: Level): Pair<Int, Int> = when (level) {
    Level.EASY -> 1 to 9
    Level.NORMAL -> 11 to 99
    Level.DIFFICULT -> 101 to 9999
}

/**
 * 掛け算・割り算用の数値範囲を取得する。
 *
 * 掛け算は結果が大きくなりやすいため、足し算・引き算とは異なる範囲を設定。
 * 割り算は掛け算と難易度を合わせるためにこの範囲を共用する。
 * - EASY: 九九の範囲（1〜9）
 * - NORMAL: 19の段まで（1〜19）
 * - DIFFICULT: 2桁同士の計算（1〜99）
 *
 * @param level 難易度レベル
 * @return 最小値と最大値のペア
 */
private fun getMultiplicationRangeForLevel(level: Level): Pair<Int, Int> = when (level) {
    Level.EASY -> 1 to 9
    Level.NORMAL -> 6 to 19
    Level.DIFFICULT -> 11 to 99
}
