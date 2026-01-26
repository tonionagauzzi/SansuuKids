package com.vitantonio.nagauzzi.sansuukids.ui.navigation.fake

import com.vitantonio.nagauzzi.sansuukids.data.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeSettingRepository : SettingRepository {
    private val mutablePerQuestionAnswerCheckEnabled = MutableStateFlow(true)
    override val perQuestionAnswerCheckEnabled: Flow<Boolean> = mutablePerQuestionAnswerCheckEnabled

    private val mutableHintDisplayEnabled = MutableStateFlow(true)
    override val hintDisplayEnabled: Flow<Boolean> = mutableHintDisplayEnabled

    override suspend fun setPerQuestionAnswerCheckEnabled(enabled: Boolean) {
        mutablePerQuestionAnswerCheckEnabled.value = enabled
    }

    override suspend fun setHintDisplayEnabled(enabled: Boolean) {
        mutableHintDisplayEnabled.value = enabled
    }
}