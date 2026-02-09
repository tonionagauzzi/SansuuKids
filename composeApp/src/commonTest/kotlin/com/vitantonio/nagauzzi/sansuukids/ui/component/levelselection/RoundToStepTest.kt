package com.vitantonio.nagauzzi.sansuukids.ui.component.levelselection

import kotlin.test.Test
import kotlin.test.assertEquals

class RoundToStepTest {

    @Test
    fun step1の場合は値がそのままスナップされる() {
        // Given: step=1, maxValue=19
        val step = 1
        val maxValue = 19

        // When/Then: 各値がそのままスナップされる
        assertEquals(1, roundToStep(1f, step, maxValue))
        assertEquals(5, roundToStep(5f, step, maxValue))
        assertEquals(10, roundToStep(10f, step, maxValue))
        assertEquals(19, roundToStep(19f, step, maxValue))
    }

    @Test
    fun step10の場合は最も近いステップにスナップされる() {
        // Given: step=10, maxValue=199
        val step = 10
        val maxValue = 199

        // When/Then: 値が最も近いステップにスナップされる
        assertEquals(11, roundToStep(11f, step, maxValue))
        assertEquals(11, roundToStep(15f, step, maxValue))
        assertEquals(21, roundToStep(19f, step, maxValue))
        assertEquals(21, roundToStep(21f, step, maxValue))
        assertEquals(101, roundToStep(101f, step, maxValue))
    }

    @Test
    fun step100の場合は最も近いステップにスナップされる() {
        // Given: step=100, maxValue=9999
        val step = 100
        val maxValue = 9999

        // When/Then: 値が最も近いステップにスナップされる
        assertEquals(1, roundToStep(1f, step, maxValue))
        assertEquals(101, roundToStep(101f, step, maxValue))
        assertEquals(501, roundToStep(450f, step, maxValue))
        assertEquals(501, roundToStep(501f, step, maxValue))
    }

    @Test
    fun 最小値の境界テスト() {
        // Given: step=10, maxValue=199
        val step = 10
        val maxValue = 199

        // When/Then: 最小値付近の値が正しくスナップされる
        assertEquals(1, roundToStep(1f, step, maxValue))
        assertEquals(1, roundToStep(2f, step, maxValue))
        assertEquals(1, roundToStep(5f, step, maxValue))
        assertEquals(11, roundToStep(6f, step, maxValue))
    }

    @Test
    fun 最大値の境界テスト() {
        // Given: step=10, maxValue=199
        val step = 10
        val maxValue = 199

        // When/Then: 最大値付近の値が正しくスナップされる
        assertEquals(191, roundToStep(191f, step, maxValue))
        assertEquals(199, roundToStep(199f, step, maxValue))
    }
}
