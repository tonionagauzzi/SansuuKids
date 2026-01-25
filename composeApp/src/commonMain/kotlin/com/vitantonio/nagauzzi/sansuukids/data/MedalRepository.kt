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
                // 既知のMode×Levelの組み合わせでキーを生成して取得
                Mode.entries.flatMap { mode ->
                    Level.entries.map { level ->
                        val key = stringPreferencesKey(MedalDisplay(mode, level, Medal.Nothing).key)
                        val medal = preferences[key]?.toMedal() ?: Medal.Nothing
                        MedalDisplay(mode, level, medal)
                    }
                }
            }
        }
}

private fun String.toMedal(): Medal = Medal.entries.firstOrNull {
    it.name == this
} ?: Medal.Nothing
