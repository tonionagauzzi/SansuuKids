package com.vitantonio.nagauzzi.sansuukids.data

import android.content.Context

object DataStoreInitializer {
    private var appContext: Context? = null

    fun initialize(context: Context) {
        appContext = context.applicationContext
    }

    internal val context: Context
        get() = requireNotNull(appContext) {
            "DataStore not initialized. Call DataStoreInitializer.initialize(context) first."
        }
}

internal actual fun getDataStorePath(): String =
    DataStoreInitializer.context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
