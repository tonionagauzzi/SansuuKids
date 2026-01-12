package com.vitantonio.nagauzzi.sansuukids.ui.navigation.key

import com.vitantonio.nagauzzi.sansuukids.model.Medal
import kotlinx.serialization.Serializable

@Serializable
internal data class ResultRoute(
    val correctCount: Int,
    val totalCount: Int,
    val medal: Medal
) : SansuuKidsRoute
