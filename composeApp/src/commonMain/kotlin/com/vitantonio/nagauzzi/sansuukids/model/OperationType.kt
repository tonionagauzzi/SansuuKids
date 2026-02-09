package com.vitantonio.nagauzzi.sansuukids.model

import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.mode_addition
import sansuukids.composeapp.generated.resources.mode_all
import sansuukids.composeapp.generated.resources.mode_division
import sansuukids.composeapp.generated.resources.mode_multiplication
import sansuukids.composeapp.generated.resources.mode_subtraction

/**
 * 演算タイプ。
 *
 * 難易度調整で使用する演算の種類。
 */
@Serializable
internal enum class OperationType {
    Addition,
    Subtraction,
    Multiplication,
    Division,
    All
}

/**
 * 演算タイプに基づいて、スライダーの下限値を取得する。
 *
 * @param level 難易度レベル
 * @return スライダー下限値
 */
internal fun OperationType.getSliderMinBound(level: Level) = when (this) {
    OperationType.Addition, OperationType.Subtraction, OperationType.All -> when (level) {
        Level.Easy -> 1
        Level.Normal -> 10
        Level.Difficult -> 100
    }

    OperationType.Multiplication, OperationType.Division -> when (level) {
        Level.Easy -> 1
        Level.Normal -> 5
        Level.Difficult -> 10
    }
}

/**
 * 演算タイプに基づいて、デフォルト出題範囲の最小値を取得する。
 *
 * @param level 難易度レベル
 * @return デフォルト出題最小値
 */
internal fun OperationType.getDefaultQuizMin(level: Level) = when(this) {
    OperationType.Addition, OperationType.Subtraction, OperationType.All -> when(level) {
        Level.Easy -> 1
        Level.Normal -> 10
        Level.Difficult -> 100
    }
    OperationType.Multiplication, OperationType.Division -> when(level) {
        Level.Easy -> 1
        Level.Normal -> 10
        Level.Difficult -> 20
    }
}

/**
 * 演算タイプに基づいて、デフォルト出題範囲の最大値を取得する。
 *
 * @param level 難易度レベル
 * @return デフォルト出題最大値
 */
internal fun OperationType.getDefaultQuizMax(level: Level) = when(this) {
    OperationType.Addition, OperationType.Subtraction, OperationType.All -> when(level) {
        Level.Easy -> 9
        Level.Normal -> 100
        Level.Difficult -> 10000
    }
    OperationType.Multiplication, OperationType.Division -> when(level) {
        Level.Easy -> 9
        Level.Normal -> 20
        Level.Difficult -> 100
    }
}

/**
 * 演算タイプに基づいて、スライダーの上限値を取得する。
 *
 * @param level 難易度レベル
 * @return スライダー上限値
 */
internal fun OperationType.getSliderMaxBound(level: Level) = when (this) {
    OperationType.Addition, OperationType.Subtraction, OperationType.All -> when (level) {
        Level.Easy -> 20
        Level.Normal -> 200
        Level.Difficult -> 10000
    }

    OperationType.Multiplication, OperationType.Division -> when (level) {
        Level.Easy -> 20
        Level.Normal -> 50
        Level.Difficult -> 200
    }
}

/**
 * 演算タイプの表示ラベルリソース。
 */
internal val OperationType.labelRes: StringResource
    get() = when (this) {
        OperationType.Addition -> Res.string.mode_addition
        OperationType.Subtraction -> Res.string.mode_subtraction
        OperationType.Multiplication -> Res.string.mode_multiplication
        OperationType.Division -> Res.string.mode_division
        OperationType.All -> Res.string.mode_all
    }
