package com.vitantonio.nagauzzi.sansuukids.model

internal data class MedalCounter(
    val mode: Mode,
    val level: Level,
    val gold: Int = 0,
    val silver: Int = 0,
    val bronze: Int = 0,
    val star: Int = 0
)

internal val MedalCounter.bestMedal: Medal
    get() = when {
        gold >= 1 -> Medal.Gold
        silver >= 1 -> Medal.Silver
        bronze >= 1 -> Medal.Bronze
        star >= 1 -> Medal.Star
        else -> Medal.Nothing
    }

internal fun MedalCounter.getCount(medal: Medal): Int {
    return when (medal) {
        Medal.Gold -> gold
        Medal.Silver -> silver
        Medal.Bronze -> bronze
        Medal.Star -> star
        Medal.Nothing -> 0
    }
}

internal fun List<MedalCounter>.findOrDefault(mode: Mode, level: Level): MedalCounter {
    return firstOrNull { it.mode == mode && it.level == level }
        ?: MedalCounter(mode = mode, level = level)
}
