package com.vitantonio.nagauzzi.sansuukids.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.vitantonio.nagauzzi.sansuukids.logic.GenerateQuiz
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import com.vitantonio.nagauzzi.sansuukids.model.operationTypes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * DataStore Preferencesを使用して難易度カスタム設定を永続化するリポジトリ。
 */
internal class DifficultyRepositoryImpl(
    private val dataStore: DataStore<Preferences> = DataStoreProvider.dataStore
) : DifficultyRepository {
    override fun getCustomRanges(mode: Mode, level: Level): Flow<List<QuizRange>> {
        return dataStore.data.map { preferences ->
            mode.operationTypes.map { operationType ->
                val defaultRange = QuizRange.Default(operationType, level)
                val min = preferences[minKey(operationType, level)] ?: defaultRange.min
                val max = preferences[maxKey(operationType, level)] ?: defaultRange.max
                QuizRange.Custom(operationType, level, min, max)
            }
        }
    }

    override suspend fun setCustomRange(
        operationType: OperationType,
        level: Level,
        min: Int,
        max: Int
    ) {
        dataStore.edit { preferences ->
            preferences[minKey(operationType, level)] = min
            preferences[maxKey(operationType, level)] = max
        }
    }

    override suspend fun resetToDefault(operationType: OperationType, level: Level) {
        dataStore.edit { preferences ->
            preferences.remove(minKey(operationType, level))
            preferences.remove(maxKey(operationType, level))
        }
    }
}

private fun minKey(operationType: OperationType, level: Level): Preferences.Key<Int> =
    intPreferencesKey("difficulty_${operationType.name.lowercase()}_${level.name.lowercase()}_min")

private fun maxKey(operationType: OperationType, level: Level): Preferences.Key<Int> =
    intPreferencesKey("difficulty_${operationType.name.lowercase()}_${level.name.lowercase()}_max")
