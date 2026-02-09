package com.vitantonio.nagauzzi.sansuukids.logic

import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import com.vitantonio.nagauzzi.sansuukids.model.operationTypes

/**
 * メダル獲得適格を判定するロジッククラス。
 *
 * カスタム設定でデフォルトより簡単（minが低い）にした場合、メダルは獲得不可とする。
 * maxを上げて難しくした場合は、メダル獲得可能。
 */
internal class MedalEligibility {
    /**
     * メダル獲得が可能かどうかを判定する。
     *
     * @param mode 計算モード
     * @param level 難易度レベル
     * @param customRanges カスタム出題範囲のリスト
     * @return メダル獲得可能な場合true
     */
    operator fun invoke(mode: Mode, level: Level, customRanges: List<QuizRange>): Boolean {
        if (customRanges.isEmpty()) return true

        return mode.operationTypes.all { operationType ->
            val customRange = customRanges.find {
                it.operationType == operationType && it.level == level
            } ?: return@all true

            val defaultRange = QuizRange.Default(operationType, level)
            customRange.min >= defaultRange.min
        }
    }
}
