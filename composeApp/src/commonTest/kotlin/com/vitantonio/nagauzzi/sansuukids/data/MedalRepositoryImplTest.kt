package com.vitantonio.nagauzzi.sansuukids.data

import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.model.MedalDisplay
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MedalRepositoryImplTest {

    @Test
    fun 初回保存でメダル回数が1になる() = runTest {
        // Given: 空のリポジトリ
        val repository = MedalRepositoryImpl(FakePreferencesDataStore())

        // When: 金メダルを保存する
        repository.save(MedalDisplay(Mode.ADDITION, Level.EASY, Medal.Gold))

        // Then: 金メダルの回数が1になる
        val display = repository.medalDisplays.first().first {
            it.mode == Mode.ADDITION && it.level == Level.EASY
        }
        assertEquals(Medal.Gold, display.medal)
        assertEquals(1, display.counts.gold)
        assertEquals(0, display.counts.silver)
        assertEquals(0, display.counts.bronze)
        assertEquals(0, display.counts.star)
    }

    @Test
    fun 同じメダルを複数回獲得で回数が増える() = runTest {
        // Given: 金メダルを1回獲得済みのリポジトリ
        val repository = MedalRepositoryImpl(FakePreferencesDataStore())
        repository.save(MedalDisplay(Mode.ADDITION, Level.EASY, Medal.Gold))

        // When: 金メダルをもう1回獲得する
        repository.save(MedalDisplay(Mode.ADDITION, Level.EASY, Medal.Gold))

        // Then: 金メダルの回数が2になる
        val display = repository.medalDisplays.first().first {
            it.mode == Mode.ADDITION && it.level == Level.EASY
        }
        assertEquals(Medal.Gold, display.medal)
        assertEquals(2, display.counts.gold)
    }

    @Test
    fun 低いメダルでも最高メダルは変わらず低いメダルの回数が増える() = runTest {
        // Given: 金メダルを獲得済みのリポジトリ
        val repository = MedalRepositoryImpl(FakePreferencesDataStore())
        repository.save(MedalDisplay(Mode.ADDITION, Level.EASY, Medal.Gold))

        // When: 銀メダルを獲得する
        repository.save(MedalDisplay(Mode.ADDITION, Level.EASY, Medal.Silver))

        // Then: 最高メダルは金のまま、銀メダルの回数が1になる
        val display = repository.medalDisplays.first().first {
            it.mode == Mode.ADDITION && it.level == Level.EASY
        }
        assertEquals(Medal.Gold, display.medal)
        assertEquals(1, display.counts.gold)
        assertEquals(1, display.counts.silver)
    }

    @Test
    fun 未獲得状態で全回数が0() = runTest {
        // Given: 空のリポジトリ
        val repository = MedalRepositoryImpl(FakePreferencesDataStore())

        // When: メダル情報を読み込む

        // Then: 全回数が0
        val display = repository.medalDisplays.first().first {
            it.mode == Mode.ADDITION && it.level == Level.EASY
        }
        assertEquals(Medal.Nothing, display.medal)
        assertEquals(0, display.counts.gold)
        assertEquals(0, display.counts.silver)
        assertEquals(0, display.counts.bronze)
        assertEquals(0, display.counts.star)
    }

    @Test
    fun Nothingメダルは回数がインクリメントされない() = runTest {
        // Given: 空のリポジトリ
        val repository = MedalRepositoryImpl(FakePreferencesDataStore())

        // When: Nothingメダルを保存する
        repository.save(MedalDisplay(Mode.ADDITION, Level.EASY, Medal.Nothing))

        // Then: 全回数が0のまま
        val display = repository.medalDisplays.first().first {
            it.mode == Mode.ADDITION && it.level == Level.EASY
        }
        assertEquals(Medal.Nothing, display.medal)
        assertEquals(0, display.counts.gold)
        assertEquals(0, display.counts.silver)
        assertEquals(0, display.counts.bronze)
        assertEquals(0, display.counts.star)
    }
}
