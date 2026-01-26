package com.vitantonio.nagauzzi.sansuukids.data

/**
 * MedalRepositoryの実体を提供するプロバイダー。
 */
internal object MedalRepositoryProvider {
    val medalRepository: MedalRepository
        get() = MedalRepositoryImpl(dataStore = DataStoreProvider.dataStore)
}
