package com.vitantonio.nagauzzi.sansuukids.model

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
internal enum class OperationType {
    Addition,
    Subtraction,
    Multiplication,
    Division,
    All
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
