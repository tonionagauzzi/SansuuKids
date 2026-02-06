package com.vitantonio.nagauzzi.sansuukids.model

internal data class MedalCount(
    val gold: Int = 0,
    val silver: Int = 0,
    val bronze: Int = 0,
    val star: Int = 0
) {
    fun getCountForMedal(medal: Medal): Int = when (medal) {
        Medal.Gold -> gold
        Medal.Silver -> silver
        Medal.Bronze -> bronze
        Medal.Star -> star
        Medal.Nothing -> 0
    }
}
