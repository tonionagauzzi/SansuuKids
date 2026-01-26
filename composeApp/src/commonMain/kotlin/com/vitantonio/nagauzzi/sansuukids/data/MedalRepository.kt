package com.vitantonio.nagauzzi.sansuukids.data

import com.vitantonio.nagauzzi.sansuukids.model.MedalDisplay
import kotlinx.coroutines.flow.Flow

/**
 * メダル情報を永続化するリポジトリ。
 */
internal interface MedalRepository {
    val medalDisplays: Flow<List<MedalDisplay>>
    suspend fun save(medalDisplay: MedalDisplay)
}
