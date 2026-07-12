package com.vitantonio.nagauzzi.sansuukids.model

/**
 * クイズの進捗を表すクラス。
 *
 * @property completedCount 完了した問題数
 * @property totalCount 総問題数
 */
internal data class QuizProgress(
    val completedCount: Int,
    val totalCount: Int
) {
    /**
     * 進捗率（0.0〜1.0）。完了した問題数を総問題数で割った値。
     * 問題が存在しない場合は完了扱いとして1.0を返す。
     */
    val rate: Float
        get() = if (totalCount == 0) 1.0f else completedCount / totalCount.toFloat()
}
