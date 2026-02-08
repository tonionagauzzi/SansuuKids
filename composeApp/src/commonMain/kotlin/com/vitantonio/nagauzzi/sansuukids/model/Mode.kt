package com.vitantonio.nagauzzi.sansuukids.model

import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.mode_addition
import sansuukids.composeapp.generated.resources.mode_all
import sansuukids.composeapp.generated.resources.mode_division
import sansuukids.composeapp.generated.resources.mode_multiplication
import sansuukids.composeapp.generated.resources.mode_subtraction

@Serializable
internal enum class Mode {
    Addition,
    Subtraction,
    Multiplication,
    Division,
    All
}

internal val Mode.labelRes: StringResource
    get() = when (this) {
        Mode.Addition -> Res.string.mode_addition
        Mode.Subtraction -> Res.string.mode_subtraction
        Mode.Multiplication -> Res.string.mode_multiplication
        Mode.Division -> Res.string.mode_division
        Mode.All -> Res.string.mode_all
    }
