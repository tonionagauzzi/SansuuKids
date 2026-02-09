package com.vitantonio.nagauzzi.sansuukids.logic.levelselection

import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import com.vitantonio.nagauzzi.sansuukids.model.getMaximumValue
import com.vitantonio.nagauzzi.sansuukids.model.getMinimumValue
import kotlin.math.roundToInt

private const val SLIDER_MIN = 1

/**
 * スライダーの値をステップに応じて丸めた新しい出題範囲を返す。
 *
 * @param quizRange 現在の出題範囲
 * @param newMin スライダーで選択された新しい最小値
 * @param newMax スライダーで選択された新しい最大値
 * @return スライダーで選択された新しい値をステップに丸めた新しい出題範囲
 */
internal fun stepQuizRange(quizRange: QuizRange, newMin: Float, newMax: Float): QuizRange {
    val minimumValue = quizRange.operationType.getMinimumValue(quizRange.level)
    val maximumValue = quizRange.operationType.getMaximumValue(quizRange.level)
    val roundedMinimum = roundToStep(newMin, minimumValue, maximumValue)
    val roundedMaximum = roundToStep(newMax, minimumValue, maximumValue)
    if (roundedMinimum < roundedMaximum) {
        return QuizRange.Custom(
            operationType = quizRange.operationType,
            level = quizRange.level,
            min = roundedMinimum,
            max = roundedMaximum
        )
    } else {
        return quizRange
    }
}

/**
 * スライダーの値をステップに応じた最寄りの値に丸める。
 *
 * @param value スライダーの現在値
 * @param step 丸める際のステップ幅
 * @param maxValue スライダーの最大値
 * @return ステップに丸められた値（SLIDER_MIN〜maxValueの範囲内）
 */
private fun roundToStep(value: Float, step: Int, maxValue: Int): Int {
    val rounded = (value / step).roundToInt() * step
    return rounded.coerceIn(SLIDER_MIN, maxValue)
}
