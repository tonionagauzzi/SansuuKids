package com.vitantonio.nagauzzi.sansuukids.model

import kotlin.test.Test
import kotlin.test.assertEquals

class MedalCounterTest {
    private val mode = Mode.Addition
    private val level = Level.Easy

    // --- bestMedal のテスト ---

    @Test
    fun メダルなしの場合bestMedalはNothingを返す() {
        // Given: メダルなしのMedalCounter
        val counter = MedalCounter(mode = mode, level = level)

        // When: bestMedalを取得する
        val result = counter.bestMedal

        // Then: Medal.Nothingが返される
        assertEquals(Medal.Nothing, result)
    }

    @Test
    fun 金メダルのみ獲得した場合bestMedalはGoldを返す() {
        // Given: 金メダルのみ獲得したMedalCounter
        val counter = MedalCounter(mode = mode, level = level, gold = 3)

        // When: bestMedalを取得する
        val result = counter.bestMedal

        // Then: Medal.Goldが返される
        assertEquals(Medal.Gold, result)
    }

    @Test
    fun 銀メダルのみ獲得した場合bestMedalはSilverを返す() {
        // Given: 銀メダルのみ獲得したMedalCounter
        val counter = MedalCounter(mode = mode, level = level, silver = 2)

        // When: bestMedalを取得する
        val result = counter.bestMedal

        // Then: Medal.Silverが返される
        assertEquals(Medal.Silver, result)
    }

    @Test
    fun 銅メダルのみ獲得した場合bestMedalはBronzeを返す() {
        // Given: 銅メダルのみ獲得したMedalCounter
        val counter = MedalCounter(mode = mode, level = level, bronze = 1)

        // When: bestMedalを取得する
        val result = counter.bestMedal

        // Then: Medal.Bronzeが返される
        assertEquals(Medal.Bronze, result)
    }

    @Test
    fun 星のみ獲得した場合bestMedalはStarを返す() {
        // Given: 星のみ獲得したMedalCounter
        val counter = MedalCounter(mode = mode, level = level, star = 5)

        // When: bestMedalを取得する
        val result = counter.bestMedal

        // Then: Medal.Starが返される
        assertEquals(Medal.Star, result)
    }

    @Test
    fun 金と銀の両方獲得した場合bestMedalはGoldを返す() {
        // Given: 金と銀の両方を獲得したMedalCounter
        val counter = MedalCounter(mode = mode, level = level, gold = 1, silver = 3)

        // When: bestMedalを取得する
        val result = counter.bestMedal

        // Then: 金メダルが優先されMedal.Goldが返される
        assertEquals(Medal.Gold, result)
    }

    @Test
    fun 銀と星の両方獲得した場合bestMedalはSilverを返す() {
        // Given: 銀と星の両方を獲得したMedalCounter
        val counter = MedalCounter(mode = mode, level = level, silver = 2, star = 4)

        // When: bestMedalを取得する
        val result = counter.bestMedal

        // Then: 銀メダルが優先されMedal.Silverが返される
        assertEquals(Medal.Silver, result)
    }

    // --- getCount のテスト ---

    @Test
    fun getCountは各メダル種類のカウントを正しく返す() {
        // Given: 各メダルに異なるカウントを持つMedalCounter
        val counter = MedalCounter(
            mode = mode,
            level = level,
            gold = 10,
            silver = 5,
            bronze = 3,
            star = 7
        )

        // When/Then: 各メダルのカウントが正しく返される
        assertEquals(10, counter.getCount(Medal.Gold))
        assertEquals(5, counter.getCount(Medal.Silver))
        assertEquals(3, counter.getCount(Medal.Bronze))
        assertEquals(7, counter.getCount(Medal.Star))
    }

    @Test
    fun getCountでNothingは常に0を返す() {
        // Given: 全てのメダルにカウントを持つMedalCounter
        val counter = MedalCounter(
            mode = mode,
            level = level,
            gold = 10,
            silver = 5,
            bronze = 3,
            star = 7
        )

        // When: Medal.Nothingのカウントを取得する
        val result = counter.getCount(Medal.Nothing)

        // Then: 0が返される
        assertEquals(0, result)
    }
}
