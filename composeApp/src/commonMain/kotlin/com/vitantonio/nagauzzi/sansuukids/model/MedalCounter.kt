package com.vitantonio.nagauzzi.sansuukids.model

internal data class MedalCounter(
    val operationType: OperationType,
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

internal fun List<MedalCounter>.findOrDefault(
    operationType: OperationType,
    level: Level
): MedalCounter {
    return firstOrNull { it.operationType == operationType && it.level == level }
        ?: MedalCounter(operationType = operationType, level = level)
}
