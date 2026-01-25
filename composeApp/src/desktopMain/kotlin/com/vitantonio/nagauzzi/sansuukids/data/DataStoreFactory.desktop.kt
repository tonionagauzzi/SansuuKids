package com.vitantonio.nagauzzi.sansuukids.data

import java.io.File

internal actual fun getDataStorePath(): String {
    val appDataDir = File(System.getProperty("user.home"), ".sansuukids")
    appDataDir.mkdirs()
    return File(appDataDir, DATA_STORE_FILE_NAME).absolutePath
}
