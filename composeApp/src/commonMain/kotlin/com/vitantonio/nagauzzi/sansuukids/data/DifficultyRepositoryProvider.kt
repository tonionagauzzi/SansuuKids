package com.vitantonio.nagauzzi.sansuukids.data

/**
 * DifficultyRepositoryの実体を提供するプロバイダー。
 */
internal object DifficultyRepositoryProvider {
    val difficultyRepository: DifficultyRepository
        get() = DifficultyRepositoryImpl(dataStore = DataStoreProvider.dataStore)
}
