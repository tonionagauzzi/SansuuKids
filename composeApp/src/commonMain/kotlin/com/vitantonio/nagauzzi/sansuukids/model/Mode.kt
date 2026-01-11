package com.vitantonio.nagauzzi.sansuukids.model

import kotlinx.serialization.Serializable

@Serializable
internal enum class Mode {
    ADDITION,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION,
    ALL
}
