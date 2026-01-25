package com.vitantonio.nagauzzi.sansuukids.data

import com.russhwolf.settings.Settings

/**
 * アプリケーション設定を永続化するリポジトリ。
 * multiplatform-settingsを使用してKey-Value形式で保存する。
 */
internal class SettingsRepository(
    private val settings: Settings = Settings()
) {
    companion object {
        private const val KEY_PER_QUESTION_ANSWER_CHECK = "per_question_answer_check"
    }

    /**
     * 1問ごとの答え合わせモードが有効かどうか。
     * 有効な場合true、無効な場合false（デフォルト: true）
     */
    var perQuestionAnswerCheckEnabled: Boolean
        get() {
            // 幼児・低学年向けアプリのため、即座のフィードバックを重視してデフォルトでONにすfる
            return settings.getBoolean(KEY_PER_QUESTION_ANSWER_CHECK, defaultValue = true)
        }
        set(value) {
            settings.putBoolean(KEY_PER_QUESTION_ANSWER_CHECK, value)
        }
}
