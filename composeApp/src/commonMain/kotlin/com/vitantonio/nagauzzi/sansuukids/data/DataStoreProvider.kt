package com.vitantonio.nagauzzi.sansuukids.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlin.concurrent.Volatile

/**
 * DataStoreのシングルトンプロバイダー。
 * アプリケーション全体で同じDataStoreインスタンスを共有する。
 */
internal object DataStoreProvider {
    @Volatile
    private var _dataStore: DataStore<Preferences>? = null

    val dataStore: DataStore<Preferences>
        get() = _dataStore ?: createDataStore(::getDataStorePath).also { _dataStore = it }

    /**
     * テスト用にDataStoreを差し替える。
     */
    fun setDataStoreForTesting(dataStore: DataStore<Preferences>) {
        _dataStore = dataStore
    }

    /**
     * DataStoreをリセットする（テスト用）。
     */
    fun reset() {
        _dataStore = null
    }
}
