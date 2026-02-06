package com.vitantonio.nagauzzi.sansuukids.model

import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.level_difficult
import sansuukids.composeapp.generated.resources.level_easy
import sansuukids.composeapp.generated.resources.level_normal

@Serializable
internal enum class Level {
    Easy,
    Normal,
    Difficult
}

internal val Level.labelRes: StringResource
    get() = when (this) {
        Level.Easy -> Res.string.level_easy
        Level.Normal -> Res.string.level_normal
        Level.Difficult -> Res.string.level_difficult
    }
