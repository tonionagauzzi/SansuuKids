package com.vitantonio.nagauzzi.sansuukids.ui.navigation.key

import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import kotlinx.serialization.Serializable

@Serializable
internal data class LevelSelectionRoute(val operationType: OperationType) : SansuuKidsRoute
