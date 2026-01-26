package com.vitantonio.nagauzzi.sansuukids.data

import kotlinx.coroutines.flow.Flow

/**
 * アプリケーション設定を永続化するリポジトリ。
 */
internal interface SettingRepository {
    val perQuestionAnswerCheckEnabled: Flow<Boolean>
    val hintDisplayEnabled: Flow<Boolean>
    suspend fun setPerQuestionAnswerCheckEnabled(enabled: Boolean)
    suspend fun setHintDisplayEnabled(enabled: Boolean)
}
