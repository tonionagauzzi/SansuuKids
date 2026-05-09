package com.vitantonio.nagauzzi.sansuukids

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vitantonio.nagauzzi.sansuukids.data.DataStoreInitializer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        DataStoreInitializer.initialize(applicationContext)

        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}
