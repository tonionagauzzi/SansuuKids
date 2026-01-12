package com.vitantonio.nagauzzi.sansuukids.logic

import com.vitantonio.nagauzzi.sansuukids.model.Medal

/**
 * 獲得したスコアを計算するロジック。
 */
internal class CalculateScore {
    /**
     * 獲得したスコアを計算する。
     *
     * @param correctCount 正解数
     * @param totalCount 総問題数
     * @return 獲得スコア（パーセント）
     */
    operator fun invoke(correctCount: Int, totalCount: Int): Int {
        if (totalCount == 0) return 0
        return ((correctCount.toFloat() / totalCount.toFloat()) * 100).toInt()
    }
}
