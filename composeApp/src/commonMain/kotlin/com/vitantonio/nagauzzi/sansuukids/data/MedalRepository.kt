package com.vitantonio.nagauzzi.sansuukids.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.model.MedalDisplay
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * メダル情報を永続化するリポジトリ。
 * DataStore Preferencesを使用してKey-Value形式で保存する。
 *
 * @param dataStore DataStoreインスタンス
 */
internal class MedalRepository(
    private val dataStore: DataStore<Preferences> = DataStoreProvider.dataStore
) {
    /**
     * メダル情報を保存する。
     * 既存のメダルより良いメダルの場合のみ更新する。
     *
     * @param medalDisplay 保存するメダル情報
     */
    suspend fun save(medalDisplay: MedalDisplay) {
        dataStore.edit { preferences ->
            val currentMedal = preferences.loadMedal(
                mode = medalDisplay.mode,
                level = medalDisplay.level
            )
            val newMedal = medalDisplay.medal
            if (currentMedal < newMedal) {
                preferences.save(medalDisplay)
            }
        }
    }

    /**
     * 保存されているメダル情報を読み込む。
     *
     * @return 保存されているメダル情報のリスト
     */
    val medalDisplays: Flow<List<MedalDisplay>>
        get() {
            return dataStore.data.map { preferences ->
                // 既知のMode×Levelの組み合わせでキーを生成して取得
                Mode.entries.flatMap { mode ->
                    Level.entries.map { level ->
                        MedalDisplay(
                            mode = mode,
                            level = level,
                            medal = preferences.loadMedal(mode, level)
                        )
                    }
                }
            }
        }
}

private fun getPreferencesKey(mode: Mode, level: Level): Preferences.Key<String> {
    return stringPreferencesKey("medal_${mode.name}_${level.name}")
}

private fun Preferences.loadMedal(mode: Mode, level: Level): Medal {
    val medalName = this[getPreferencesKey(mode, level)]
    return Medal.entries.firstOrNull { it.name == medalName } ?: Medal.Nothing
}

private fun MutablePreferences.save(medalDisplay: MedalDisplay) {
    this[getPreferencesKey(medalDisplay.mode, medalDisplay.level)] = medalDisplay.medal.name
}
