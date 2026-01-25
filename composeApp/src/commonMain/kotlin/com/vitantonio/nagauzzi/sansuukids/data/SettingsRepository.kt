package com.vitantonio.nagauzzi.sansuukids.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * アプリケーション設定を永続化するリポジトリ。
 * DataStore Preferencesを使用してKey-Value形式で保存する。
 */
internal class SettingsRepository(
    private val dataStore: DataStore<Preferences> = DataStoreProvider.dataStore
) {
    companion object {
        private val KEY_PER_QUESTION_ANSWER_CHECK =
            booleanPreferencesKey("per_question_answer_check")
        private val KEY_HINT_DISPLAY = booleanPreferencesKey("hint_display")
    }

    /**
     * 1問ごとの答え合わせモードが有効かどうか。
     * 有効な場合true、無効な場合false（デフォルト: true）
     */
    val perQuestionAnswerCheckEnabled: Flow<Boolean>
        get() {
            return dataStore.data.map { preferences ->
                // 幼児・低学年向けアプリのため、即座のフィードバックを重視してデフォルトでONにする
                preferences[KEY_PER_QUESTION_ANSWER_CHECK] ?: true
            }
        }

    /**
     * ヒント表示が有効かどうか（かんたんモードのみ適用）。
     * 有効な場合true、無効な場合false（デフォルト: true）
     */
    val hintDisplayEnabled: Flow<Boolean>
        get() {
            return dataStore.data.map { preferences ->
                // 幼児向けアプリのため、ヒントありをデフォルトにする
                preferences[KEY_HINT_DISPLAY] ?: true
            }
        }

    /**
     * 1問ごとの答え合わせモードの有効/無効を設定する。
     */
    suspend fun setPerQuestionAnswerCheckEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_PER_QUESTION_ANSWER_CHECK] = enabled
        }
    }

    /**
     * ヒント表示の有効/無効を設定する。
     */
    suspend fun setHintDisplayEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_HINT_DISPLAY] = enabled
        }
    }
}
