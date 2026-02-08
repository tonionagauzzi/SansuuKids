package com.vitantonio.nagauzzi.sansuukids.data

import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.model.Medal.Bronze
import com.vitantonio.nagauzzi.sansuukids.model.Medal.Gold
import com.vitantonio.nagauzzi.sansuukids.model.Medal.Star
import com.vitantonio.nagauzzi.sansuukids.model.Medal.Silver
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

        // When: 同じmode×levelに異なるメダルを追加する（金メダルは2回追加する）
        repository.add(mode = Mode.Addition, level = Level.Easy, medal = Gold)
        repository.add(mode = Mode.Addition, level = Level.Easy, medal = Gold)
        repository.add(mode = Mode.Addition, level = Level.Easy, medal = Silver)
        repository.add(mode = Mode.Addition, level = Level.Easy, medal = Bronze)
        repository.add(mode = Mode.Addition, level = Level.Easy, medal = Star)

        // Then: 金メダルのカウントは2に、他のメダルのカウントは1になる
        val counter = repository.medalCounters.first().first {
            it.mode == Mode.Addition && it.level == Level.Easy
        }
        assertEquals(2, counter.gold)
        assertEquals(1, counter.silver)
        assertEquals(1, counter.bronze)
        assertEquals(1, counter.star)
    }

    @Test
    fun メダルなしは加算されない() = runTest {
        // Given: 空のリポジトリ
        val repository = MedalRepositoryImpl(FakePreferencesDataStore())

        // When: Medal.Nothingを追加する
        repository.add(mode = Mode.Addition, level = Level.Easy, medal = Medal.Nothing)

        // Then: すべてのメダルのカウントが0のまま
        val counter = repository.medalCounters.first().first {
            it.mode == Mode.Addition && it.level == Level.Easy
        }
        assertEquals(0, counter.gold)
        assertEquals(0, counter.silver)
        assertEquals(0, counter.bronze)
        assertEquals(0, counter.star)
    }

    @Test
    fun 異なるモードレベルのカウントは独立している() = runTest {
        // Given: 空のリポジトリ
        val repository = MedalRepositoryImpl(FakePreferencesDataStore())

        // When: Addition×Easyに金メダルを追加する
        repository.add(mode = Mode.Addition, level = Level.Easy, medal = Gold)

        // Then: Addition×Easyの金メダルが1になり、他の組み合わせは影響を受けない
        val counters = repository.medalCounters.first()
        val additionEasy = counters.first {
            it.mode == Mode.Addition && it.level == Level.Easy
        }
        assertEquals(1, additionEasy.gold)
        val additionNormal = counters.first {
            it.mode == Mode.Addition && it.level == Level.Normal
        }
        assertEquals(0, additionNormal.gold)
        val subtractionEasy = counters.first {
            it.mode == Mode.Subtraction && it.level == Level.Easy
        }
        assertEquals(0, subtractionEasy.gold)
    }
}
