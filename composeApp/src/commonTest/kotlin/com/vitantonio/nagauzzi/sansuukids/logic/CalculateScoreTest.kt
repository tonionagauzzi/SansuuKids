package com.vitantonio.nagauzzi.sansuukids.logic

import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateScoreTest {
    private val calculateScore = CalculateScore()

    @Test
    fun 正解数と総問題数から正しいスコアが計算される_全問正解() {
        // Given: 10問中10問正解
        val correctCount = 10
        val totalCount = 10

        // When: スコアを計算する
        val score = calculateScore(correctCount, totalCount)

        // Then: 100点
        assertEquals(100, score)
    }

    @Test
    fun 正解数と総問題数から正しいスコアが計算される_3分の1_小数点以下切り捨て() {
        // Given: 3問中1問正解（1/3 = 0.333... → 33）
        val correctCount = 1
        val totalCount = 3

        // When: スコアを計算する
        val score = calculateScore(correctCount, totalCount)

        // Then: 33点（切り捨て）
        assertEquals(33, score)
    }

    @Test
    fun 正解数と総問題数から正しいスコアが計算される_全問不正解() {
        // Given: 0問中0問正解（ゼロ除算のケース）
        val correctCount = 0
        val totalCount = 0

        // When: スコアを計算する
        val score = calculateScore(correctCount, totalCount)

        // Then: 0点
        assertEquals(0, score)
    }
}
