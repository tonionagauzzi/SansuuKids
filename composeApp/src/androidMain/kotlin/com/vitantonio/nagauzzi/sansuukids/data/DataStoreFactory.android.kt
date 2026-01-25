package com.vitantonio.nagauzzi.sansuukids.data

import android.content.Context

private var appContext: Context? = null

internal fun initializeDataStore(context: Context) {
    appContext = context.applicationContext
}

internal actual fun getDataStorePath(): String {
    return requireNotNull(appContext) {
        "DataStore not initialized. Call initializeDataStore(context) first."
    }.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
}
