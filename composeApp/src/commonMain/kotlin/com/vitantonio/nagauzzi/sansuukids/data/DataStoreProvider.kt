package com.vitantonio.nagauzzi.sansuukids.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlin.concurrent.Volatile
import okio.Path.Companion.toPath

/**
 * DataStoreのシングルトンプロバイダー。
 * アプリケーション全体で同じDataStoreインスタンスを共有する。
 */
internal object DataStoreProvider {
    @Volatile
    private var _dataStore: DataStore<Preferences>? = null

    /**
     * DataStoreのインスタンス。
     * 初回アクセス時に作成され、その後は同じインスタンスが返される。
     *
     * @return DataStoreのシングルトンインスタンス
     */
    val dataStore: DataStore<Preferences>
        get() = _dataStore ?: PreferenceDataStoreFactory.createWithPath {
            getDataStorePath().toPath()
        }.also { createdDataStore ->
            _dataStore = createdDataStore
        }
}
