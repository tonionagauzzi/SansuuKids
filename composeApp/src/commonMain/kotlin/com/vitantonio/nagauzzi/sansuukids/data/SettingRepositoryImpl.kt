package com.vitantonio.nagauzzi.sansuukids.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * DataStore Preferencesを使用してアプリケーション設定をKey-Value形式で永続化するリポジトリ。
 */
internal class SettingRepositoryImpl(
    private val dataStore: DataStore<Preferences> = DataStoreProvider.dataStore
) : SettingRepository {
    private companion object {
        val KEY_PER_QUESTION_ANSWER_CHECK = booleanPreferencesKey("per_question_answer_check")
        val KEY_HINT_DISPLAY = booleanPreferencesKey("hint_display")
    }

    /**
     * 1問ごとの答え合わせモードが有効かどうか。
     *
     * @return 有効な場合true、無効な場合false（デフォルト: true）
     */
    override val perQuestionAnswerCheckEnabled: Flow<Boolean>
        get() {
            return dataStore.data.map { preferences ->
                // 幼児・低学年向けアプリのため、即座のフィードバックを重視してデフォルトでONにする
                preferences[KEY_PER_QUESTION_ANSWER_CHECK] ?: true
            }
        }

    /**
     * ヒント表示が有効かどうか（かんたんモードのみ適用）。
     *
     * @return 有効な場合true、無効な場合false（デフォルト: true）
     */
    override val hintDisplayEnabled: Flow<Boolean>
        get() {
            return dataStore.data.map { preferences ->
                // 幼児向けアプリのため、ヒントありをデフォルトにする
                preferences[KEY_HINT_DISPLAY] ?: true
            }
        }

    /**
     * 1問ごとの答え合わせモードの有効/無効を設定する。
     */
    override suspend fun setPerQuestionAnswerCheckEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_PER_QUESTION_ANSWER_CHECK] = enabled
        }
    }

    /**
     * ヒント表示の有効/無効を設定する。
     */
    override suspend fun setHintDisplayEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_HINT_DISPLAY] = enabled
        }
    }
}
