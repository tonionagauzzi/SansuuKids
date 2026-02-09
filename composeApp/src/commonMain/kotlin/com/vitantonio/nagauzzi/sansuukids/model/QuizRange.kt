package com.vitantonio.nagauzzi.sansuukids.model

/**
 * 出題範囲。
 */
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
    data class Default(
        override val operationType: OperationType,
        override val level: Level
    ) : QuizRange {
        override val min: Int
            get() = when(operationType) {
                OperationType.Addition, OperationType.Subtraction -> when(level) {
                    Level.Easy -> 1
                    Level.Normal -> 11
                    Level.Difficult -> 101
                }
                OperationType.Multiplication, OperationType.Division -> when(level) {
                    Level.Easy -> 1
                    Level.Normal -> 6
                    Level.Difficult -> 11
                }
            }
        override val max: Int
            get() = when(operationType) {
                OperationType.Addition, OperationType.Subtraction -> when(level) {
                    Level.Easy -> 9
                    Level.Normal -> 99
                    Level.Difficult -> 9999
                }
                OperationType.Multiplication, OperationType.Division -> when(level) {
                    Level.Easy -> 9
                    Level.Normal -> 19
                    Level.Difficult -> 99
                }
            }
    }

    /**
     * カスタム出題範囲。
     *
     * @param operationType 演算タイプ
     * @param level 難易度レベル
     * @param min 最小値
     * @param max 最大値
     */
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
