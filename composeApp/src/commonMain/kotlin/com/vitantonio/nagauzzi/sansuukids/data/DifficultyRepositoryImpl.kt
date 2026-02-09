package com.vitantonio.nagauzzi.sansuukids.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * DataStore Preferencesを使用して難易度カスタム設定を永続化するリポジトリ。
 */
internal class DifficultyRepositoryImpl(
    private val dataStore: DataStore<Preferences> = DataStoreProvider.dataStore
) : DifficultyRepository {
    override fun getQuizRange(operationType: OperationType, level: Level): Flow<QuizRange> {
        return dataStore.data.map { preferences ->
            val defaultRange = QuizRange.Default(operationType, level)
            val min = preferences[minKey(operationType, level)] ?: return@map defaultRange
            val max = preferences[maxKey(operationType, level)] ?: return@map defaultRange
            QuizRange.Custom(operationType, level, min, max)
        }
    }

    override suspend fun set(quizRange: QuizRange) {
        dataStore.edit { preferences ->
            preferences[minKey(quizRange.operationType, quizRange.level)] = quizRange.min
            preferences[maxKey(quizRange.operationType, quizRange.level)] = quizRange.max
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
