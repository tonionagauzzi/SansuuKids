package com.vitantonio.nagauzzi.sansuukids.model

import kotlin.test.Test
import kotlin.test.assertEquals

class QuizProgressTest {
    @Test
    fun rateは完了した問題数に応じた進捗率を返す() {
        // Given: 全10問中3問完了した進捗
        val progress = QuizProgress(completedCount = 3, totalCount = 10)

        // When: 進捗率を取得する
        val rate = progress.rate

        // Then: 進捗率は0.3（3/10）
        assertEquals(0.3f, rate)
    }

    @Test
    fun rateは問題が存在しない場合は完了扱いとして1を返す() {
        // Given: 問題が存在しない進捗
        val progress = QuizProgress(completedCount = 0, totalCount = 0)

        // When: 進捗率を取得する
        val rate = progress.rate

        // Then: 完了扱いとして進捗率は1.0
        assertEquals(1.0f, rate)
    }
}
