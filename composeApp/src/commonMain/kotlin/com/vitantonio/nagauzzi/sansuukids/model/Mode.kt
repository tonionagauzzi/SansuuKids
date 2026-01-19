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
    ADDITION,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION,
    ALL
}

internal val Mode.labelRes: StringResource
    get() = when (this) {
        Mode.ADDITION -> Res.string.mode_addition
        Mode.SUBTRACTION -> Res.string.mode_subtraction
        Mode.MULTIPLICATION -> Res.string.mode_multiplication
        Mode.DIVISION -> Res.string.mode_division
        Mode.ALL -> Res.string.mode_all
    }
