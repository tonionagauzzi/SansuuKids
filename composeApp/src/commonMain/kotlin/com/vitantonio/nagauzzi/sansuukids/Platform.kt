package com.vitantonio.nagauzzi.sansuukids

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform