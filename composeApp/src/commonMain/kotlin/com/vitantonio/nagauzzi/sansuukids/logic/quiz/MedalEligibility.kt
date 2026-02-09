package com.vitantonio.nagauzzi.sansuukids.logic.quiz

import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange

/**
 * メダル獲得適格を判定するロジッククラス。
 *
 * カスタム設定でデフォルトより簡単（minが低い、またはmaxが低い）にした場合、メダルは獲得不可とする。
 * minやmaxを上げて難しくした場合は、メダル獲得可能。
 */
internal class MedalEligibility {
    /**
     * メダル獲得が可能かどうかを判定する。
     *
     * @param operationType 演算タイプ
     * @param level 難易度レベル
     * @param quizRange カスタム出題範囲
     * @return メダル獲得可能な場合true
     */
    operator fun invoke(operationType: OperationType, level: Level, quizRange: QuizRange): Boolean {
        val defaultRange = QuizRange.Default(operationType, level)
        return quizRange.min >= defaultRange.min && quizRange.max >= defaultRange.max
    }
}
