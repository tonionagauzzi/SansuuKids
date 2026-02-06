package com.vitantonio.nagauzzi.sansuukids.model

internal data class MedalDisplay(
    val mode: Mode,
    val level: Level,
    val medal: Medal,
    val counts: MedalCount = MedalCount()
)
