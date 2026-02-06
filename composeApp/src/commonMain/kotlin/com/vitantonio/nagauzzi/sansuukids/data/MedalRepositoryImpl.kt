package com.vitantonio.nagauzzi.sansuukids.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.model.MedalCount
import com.vitantonio.nagauzzi.sansuukids.model.MedalDisplay
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
    override val medalDisplays: Flow<List<MedalDisplay>>
        get() {
            return dataStore.data.map { preferences ->
                // 既知のMode×Levelの組み合わせでキーを生成して取得
                Mode.entries.flatMap { mode ->
                    Level.entries.map { level ->
                        MedalDisplay(
                            mode = mode,
                            level = level,
                            medal = preferences.loadMedal(mode, level),
                            counts = preferences.loadMedalCount(mode, level)
                        )
                    }
                }
            }
        }

    /**
     * メダル情報を保存する。
     * 既存のメダルより良いメダルの場合のみ最高メダルを更新する。
     * メダルの獲得回数は常にインクリメントする。
     *
     * @param medalDisplay 保存するメダル情報
     */
    override suspend fun save(medalDisplay: MedalDisplay) {
        dataStore.edit { preferences ->
            val newMedal = medalDisplay.medal
            if (newMedal != Medal.Nothing) {
                // 獲得回数をインクリメント
                preferences.incrementCount(medalDisplay.mode, medalDisplay.level, newMedal)
            }
            val currentMedal = preferences.loadMedal(
                mode = medalDisplay.mode,
                level = medalDisplay.level
            )
            if (currentMedal < newMedal) {
                preferences.saveMedal(medalDisplay)
            }
        }
    }
}

private fun getMedalPreferencesKey(mode: Mode, level: Level): Preferences.Key<String> {
    return stringPreferencesKey("medal_${mode.name}_${level.name}")
}

private fun getCountPreferencesKey(mode: Mode, level: Level, medal: Medal): Preferences.Key<Int> {
    return intPreferencesKey("count_${mode.name}_${level.name}_${medal.name}")
}

private fun Preferences.loadMedal(mode: Mode, level: Level): Medal {
    val medalName = this[getMedalPreferencesKey(mode, level)]
    return Medal.entries.firstOrNull { it.name == medalName } ?: Medal.Nothing
}

private fun Preferences.loadMedalCount(mode: Mode, level: Level): MedalCount {
    return MedalCount(
        gold = this[getCountPreferencesKey(mode, level, Medal.Gold)] ?: 0,
        silver = this[getCountPreferencesKey(mode, level, Medal.Silver)] ?: 0,
        bronze = this[getCountPreferencesKey(mode, level, Medal.Bronze)] ?: 0,
        star = this[getCountPreferencesKey(mode, level, Medal.Star)] ?: 0
    )
}

private fun MutablePreferences.saveMedal(medalDisplay: MedalDisplay) {
    this[getMedalPreferencesKey(medalDisplay.mode, medalDisplay.level)] = medalDisplay.medal.name
}

private fun MutablePreferences.incrementCount(mode: Mode, level: Level, medal: Medal) {
    val key = getCountPreferencesKey(mode, level, medal)
    val currentCount = this[key] ?: 0
    this[key] = currentCount + 1
}
