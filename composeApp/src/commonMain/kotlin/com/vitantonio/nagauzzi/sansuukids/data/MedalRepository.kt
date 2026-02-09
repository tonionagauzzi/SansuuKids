package com.vitantonio.nagauzzi.sansuukids.data

import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.model.MedalCounter
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import kotlinx.coroutines.flow.Flow

/**
 * メダル情報を永続化するリポジトリ。
 */
internal interface MedalRepository {
    val medalCounters: Flow<List<MedalCounter>>
    suspend fun add(operationType: OperationType, level: Level, medal: Medal)
}
