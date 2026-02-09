package com.vitantonio.nagauzzi.sansuukids.logic

import com.vitantonio.nagauzzi.sansuukids.logic.quiz.AwardMedal
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import kotlin.test.Test
import kotlin.test.assertEquals

class AwardMedalTest {
    private val awardMedal = AwardMedal()

    @Test
    fun 正答率100パーセントで金メダルを授与する() {
        // Given: 10問中10問正解
        val correctCount = 10
        val totalCount = 10

        // When: メダルを授与する
        val medal = awardMedal(true, correctCount, totalCount)

        // Then: 金メダル
        assertEquals(Medal.Gold, medal)
    }

    @Test
    fun 正答率90パーセントで銀メダルを授与する() {
        // Given: 10問中9問正解
        val correctCount = 9
        val totalCount = 10

        // When: メダルを授与する
        val medal = awardMedal(true, correctCount, totalCount)

        // Then: 銀メダル
        assertEquals(Medal.Silver, medal)
    }

    @Test
    fun 正答率80パーセントで銀メダルを授与する() {
        // Given: 10問中8問正解
        val correctCount = 8
        val totalCount = 10

        // When: メダルを授与する
        val medal = awardMedal(true, correctCount, totalCount)

        // Then: 銀メダル
        assertEquals(Medal.Silver, medal)
    }

    @Test
    fun 正答率70パーセントで銅メダルを授与する() {
        // Given: 10問中7問正解
        val correctCount = 7
        val totalCount = 10

        // When: メダルを授与する
        val medal = awardMedal(true, correctCount, totalCount)

        // Then: 銅メダル
        assertEquals(Medal.Bronze, medal)
    }

    @Test
    fun 正答率60パーセントで銅メダルを授与する() {
        // Given: 10問中6問正解
        val correctCount = 6
        val totalCount = 10

        // When: メダルを授与する
        val medal = awardMedal(true, correctCount, totalCount)

        // Then: 銅メダル
        assertEquals(Medal.Bronze, medal)
    }

    @Test
    fun 正答率50パーセントでスターを授与する() {
        // Given: 10問中5問正解
        val correctCount = 5
        val totalCount = 10

        // When: メダルを授与する
        val medal = awardMedal(true, correctCount, totalCount)

        // Then: スター
        assertEquals(Medal.Star, medal)
    }

    @Test
    fun 全ての問題に回答していない場合は何も授与しない() {
        // Given: 9問中5問正解
        val correctCount = 5
        val totalCount = 9

        // When: メダルを授与する
        val medal = awardMedal(false, correctCount, totalCount)

        // Then: なし
        assertEquals(Medal.Nothing, medal)
    }

    @Test
    fun 問題数が0の場合は何も授与しない() {
        // Given: 0問中0問正解
        val correctCount = 0
        val totalCount = 0

        // When: メダルを授与する
        val medal = awardMedal(true, correctCount, totalCount)

        // Then: なし
        assertEquals(Medal.Nothing, medal)
    }
}
