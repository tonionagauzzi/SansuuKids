package com.vitantonio.nagauzzi.sansuukids.model

internal data class MedalDisplay(
    val mode: Mode,
    val level: Level,
    val medal: Medal
)

internal val MedalDisplay.key: String
    get() = "medal_${mode.name}_${level.name}"
