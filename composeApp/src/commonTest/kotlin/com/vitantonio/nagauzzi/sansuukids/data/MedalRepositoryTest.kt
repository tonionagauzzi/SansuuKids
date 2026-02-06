package com.vitantonio.nagauzzi.sansuukids.data

import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Medal.Gold
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MedalRepositoryTest {
    @Test
    fun 獲得したメダルが加算される() = runTest {
        // Given: 空のリポジトリ
        val repository = MedalRepositoryImpl(FakePreferencesDataStore())

        // When: 金メダルを獲得する
        repository.add(mode = Mode.Addition, level = Level.Easy, medal = Gold)

        // Then: 金メダルの回数が1になる
        val counter1 = repository.medalCounters.first().first {
            it.mode == Mode.Addition && it.level == Level.Easy
        }
        assertEquals(1, counter1.gold)
        assertEquals(0, counter1.silver)
        assertEquals(0, counter1.bronze)
        assertEquals(0, counter1.star)

        // When: 金メダルをもう1回獲得する
        repository.add(mode = Mode.Addition, level = Level.Easy, medal = Gold)

        // Then: 金メダルの回数が2になる
        val counter2 = repository.medalCounters.first().first {
            it.mode == Mode.Addition && it.level == Level.Easy
        }
        assertEquals(2, counter2.gold)
        assertEquals(0, counter2.silver)
        assertEquals(0, counter2.bronze)
        assertEquals(0, counter2.star)
    }
}
