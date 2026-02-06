package com.vitantonio.nagauzzi.sansuukids.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.model.MedalCounter
import com.vitantonio.nagauzzi.sansuukids.model.Mode
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
                Mode.entries.flatMap { mode ->
                    Level.entries.map { level ->
                        preferences.getMedalCounter(mode = mode, level = level)
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
    override suspend fun add(mode: Mode, level: Level, medal: Medal) {
        dataStore.edit { preferences ->
            if (medal != Medal.Nothing) {
                preferences.incrementMedalCount(mode = mode, level = level, medal = medal)
            }
        }
    }
}

private fun getMedalCountPreferencesKey(
    mode: Mode,
    level: Level,
    medal: Medal
): Preferences.Key<Int> {
    return intPreferencesKey("count_${mode.name}_${level.name}_${medal.name}")
}

private fun Preferences.getMedalCounter(mode: Mode, level: Level): MedalCounter {
    return MedalCounter(
        mode = mode,
        level = level,
        gold = this[getMedalCountPreferencesKey(mode, level, Medal.Gold)] ?: 0,
        silver = this[getMedalCountPreferencesKey(mode, level, Medal.Silver)] ?: 0,
        bronze = this[getMedalCountPreferencesKey(mode, level, Medal.Bronze)] ?: 0,
        star = this[getMedalCountPreferencesKey(mode, level, Medal.Star)] ?: 0
    )
}

private fun MutablePreferences.incrementMedalCount(
    mode: Mode,
    level: Level,
    medal: Medal
) {
    val key = getMedalCountPreferencesKey(
        mode = mode,
        level = level,
        medal = medal
    )
    val currentCount = this[key] ?: 0
    this[key] = currentCount + 1
}
