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
        override val min: Int get() = operationType.getDefaultMinimumValue(level)
        override val max: Int get() = operationType.getDefaultMaximumValue(level)
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
