package com.vitantonio.nagauzzi.sansuukids.ui.navigation.fake

import com.vitantonio.nagauzzi.sansuukids.data.DifficultyRepository
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

internal class FakeDifficultyRepository : DifficultyRepository {
    private val customRanges = MutableStateFlow<List<QuizRange.Custom>>(emptyList())

    override fun getCustomRange(operationType: OperationType, level: Level): Flow<QuizRange> {
        return customRanges.map { ranges ->
            ranges.find {
                it.operationType == operationType && it.level == level
            } ?: QuizRange.Default(operationType, level)
        }
    }

    override suspend fun setCustomRange(
        operationType: OperationType,
        level: Level,
        min: Int,
        max: Int
    ) {
        val newRange = QuizRange.Custom(operationType, level, min, max)
        customRanges.value = customRanges.value
            .filter { !(it.operationType == operationType && it.level == level) } + newRange
    }

    override suspend fun resetToDefault(operationType: OperationType, level: Level) {
        customRanges.value = customRanges.value
            .filter { !(it.operationType == operationType && it.level == level) }
    }
}
