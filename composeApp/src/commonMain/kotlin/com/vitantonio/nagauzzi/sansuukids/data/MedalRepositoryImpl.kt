package com.vitantonio.nagauzzi.sansuukids.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.model.MedalCounter
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * DataStore Preferencesを使用してメダル情報をKey-Value形式で永続化するリポジトリの実装。
 *
 * @param dataStore DataStore<Preferences>のインスタンス
 */
internal class MedalRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : MedalRepository {
    /**
     * 保存されているメダル情報を読み込む。
     *
     * @return 保存されているメダル情報のリスト
     */
    override val medalCounters: Flow<List<MedalCounter>>
        get() {
            return dataStore.data.map { preferences ->
                OperationType.entries.flatMap { operationType ->
                    Level.entries.map { level ->
                        preferences.getMedalCounter(operationType = operationType, level = level)
                    }
                }
            }
        }

    /**
     * 獲得したメダルを保存する。
     *
     * @param mode プレイしたモード
     * @param level プレイしたレベル
     * @param medal 獲得したメダル
     */
    override suspend fun add(operationType: OperationType, level: Level, medal: Medal) {
        dataStore.edit { preferences ->
            if (medal != Medal.Nothing) {
                preferences.incrementMedalCount(
                    operationType = operationType,
                    level = level,
                    medal = medal
                )
            }
        }
    }
}

private fun getMedalCountPreferencesKey(
    operationType: OperationType,
    level: Level,
    medal: Medal
): Preferences.Key<Int> {
    val modeName = operationType.name.lowercase()
    val levelName = level.name.lowercase()
    val medalName = medal.name.lowercase()
    return intPreferencesKey("count_${modeName}_${levelName}_${medalName}")
}

private fun Preferences.getMedalCounter(operationType: OperationType, level: Level): MedalCounter {
    return MedalCounter(
        operationType = operationType,
        level = level,
        gold = this[getMedalCountPreferencesKey(operationType, level, Medal.Gold)] ?: 0,
        silver = this[getMedalCountPreferencesKey(operationType, level, Medal.Silver)] ?: 0,
        bronze = this[getMedalCountPreferencesKey(operationType, level, Medal.Bronze)] ?: 0,
        star = this[getMedalCountPreferencesKey(operationType, level, Medal.Star)] ?: 0
    )
}

private fun MutablePreferences.incrementMedalCount(
    operationType: OperationType,
    level: Level,
    medal: Medal
) {
    val key = getMedalCountPreferencesKey(
        operationType = operationType,
        level = level,
        medal = medal
    )
    val currentCount = this[key] ?: 0
    this[key] = currentCount + 1
}
