package com.vitantonio.nagauzzi.sansuukids.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.model.MedalDisplay
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.model.key
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * メダル情報を永続化するリポジトリ。
 * DataStore Preferencesを使用してKey-Value形式で保存する。
 */
internal class MedalRepository(
    private val dataStore: DataStore<Preferences> = DataStoreProvider.dataStore
) {
    /**
     * メダルを保存する。
     * 既存のメダルより良いメダルの場合のみ更新する。
     */
    suspend fun saveMedal(medalDisplay: MedalDisplay) {
        val key = stringPreferencesKey(medalDisplay.key)
        dataStore.edit { preferences ->
            val currentMedal = preferences[key]?.toMedal() ?: Medal.Nothing
            val newMedal = medalDisplay.medal
            if (currentMedal < newMedal) {
                preferences[key] = newMedal.name
            }
        }
    }

    /**
     * 保存されているメダル一覧を取得する。
     * @return 保存されているメダル一覧
     */
    val medalDisplays: Flow<List<MedalDisplay>>
        get() {
            return dataStore.data.map { preferences ->
                // 保存されている全メダルをMedalDisplayのリストで返す
                preferences.asMap().mapNotNull { (key, value) ->
                    val medal = (value as? String)?.toMedal() ?: return@mapNotNull null
                    key.toMedalDisplay(medal)
                }.toList()
            }
        }
}

private fun String.toMedal(): Medal = Medal.entries.firstOrNull {
    it.name == this
} ?: Medal.Nothing

private fun Preferences.Key<*>.toMedalDisplay(medal: Medal): MedalDisplay = name
    .split("_")
    .takeLast(2)
    .let { modeAndLevel ->
        val mode = Mode.valueOf(modeAndLevel[0])
        val level = Level.valueOf(modeAndLevel[1])
        MedalDisplay(mode, level, medal)
    }
