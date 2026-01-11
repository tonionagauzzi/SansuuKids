package com.vitantonio.nagauzzi.sansuukids.navigation.key

import com.vitantonio.nagauzzi.sansuukids.model.Mode
import kotlinx.serialization.Serializable

@Serializable
internal data class LevelSelectionRoute(val mode: Mode) : SansuuKidsRoute
