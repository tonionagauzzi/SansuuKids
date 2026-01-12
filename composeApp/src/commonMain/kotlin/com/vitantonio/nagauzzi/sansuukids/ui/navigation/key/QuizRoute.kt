package com.vitantonio.nagauzzi.sansuukids.ui.navigation.key

import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import kotlinx.serialization.Serializable

@Serializable
internal data class QuizRoute(val mode: Mode, val level: Level) : SansuuKidsRoute
