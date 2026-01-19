package com.vitantonio.nagauzzi.sansuukids.model

import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.level_difficult
import sansuukids.composeapp.generated.resources.level_easy
import sansuukids.composeapp.generated.resources.level_normal

@Serializable
internal enum class Level {
    EASY,
    NORMAL,
    DIFFICULT
}

internal val Level.labelRes: StringResource
    get() = when (this) {
        Level.EASY -> Res.string.level_easy
        Level.NORMAL -> Res.string.level_normal
        Level.DIFFICULT -> Res.string.level_difficult
    }
