package com.vitantonio.nagauzzi.sansuukids.model

import kotlinx.serialization.Serializable

/**
 * 出題範囲。
 */
@Serializable
internal sealed interface QuizRange {
    val operationType: OperationType
    val level: Level
    val min: Int
    val max: Int

    /**
     * デフォルトの出題範囲。
     *
     * @param operationType 演算タイプ
     * @param level 難易度レベル
     */
    @Serializable
    data class Default(
        override val operationType: OperationType,
        override val level: Level
    ) : QuizRange {
        override val min: Int get() = operationType.getDefaultQuizMin(level)
        override val max: Int get() = operationType.getDefaultQuizMax(level)
    }

    /**
     * カスタム出題範囲。
     *
     * @param operationType 演算タイプ
     * @param level 難易度レベル
     * @param min 最小値
     * @param max 最大値
     */
    @Serializable
    data class Custom(
        override val operationType: OperationType,
        override val level: Level,
        override val min: Int,
        override val max: Int
    ) : QuizRange {
        init {
            require(min >= 1) { "min ($min) must be at least 1" }
            require(min <= max) { "min ($min) must be less than or equal to max ($max)" }
        }
    }
}

/**
 * メダル獲得が可能かどうかを判定する。
 * カスタム設定でデフォルトより簡単（minが低い、またはmaxが低い）にした場合、メダルは獲得不可とする。
 * minやmaxを上げて難しくした場合は、メダル獲得可能。
 *
 * @return メダル獲得可能な場合true
 */
internal val QuizRange.isMedalEnabled: Boolean
    get() {
        val defaultRange = QuizRange.Default(operationType, level)
        return min >= defaultRange.min && max >= defaultRange.max
    }
