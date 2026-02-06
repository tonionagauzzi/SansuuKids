package com.vitantonio.nagauzzi.sansuukids.model

internal data class MedalCounter(
    val mode: Mode,
    val level: Level,
    val gold: Int,
    val silver: Int,
    val bronze: Int,
    val star: Int
)

internal fun MedalCounter.getCount(medal: Medal): Int {
    return when (medal) {
        Medal.Gold -> gold
        Medal.Silver -> silver
        Medal.Bronze -> bronze
        Medal.Star -> star
        Medal.Nothing -> 0
    }
}
