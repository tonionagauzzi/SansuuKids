package com.vitantonio.nagauzzi.sansuukids.data

/**
 * MedalRepositoryの実体を提供するプロバイダー。
 */
internal object SettingRepositoryProvider {
    val settingRepository: SettingRepository
        get() = SettingRepositoryImpl(dataStore = DataStoreProvider.dataStore)
}
