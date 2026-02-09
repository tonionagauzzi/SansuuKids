package com.vitantonio.nagauzzi.sansuukids.logic.quiz

import com.vitantonio.nagauzzi.sansuukids.model.Medal

/**
 * 正答率に基づいてメダルを授与するロジック。
 */
internal class AwardMedal {
    /**
     * 正答率に基づいてメダルを授与する。
     *
     * @param isQuizComplete クイズが完了している場合はtrue、未完了の場合はfalse
     * @param correctCount 正解数
     * @param totalCount 総問題数
     * @return 獲得メダル
     */
    operator fun invoke(isQuizComplete: Boolean, correctCount: Int, totalCount: Int): Medal {
        if (!isQuizComplete || totalCount == 0) return Medal.Nothing
        return when {
            correctCount * 100 >= totalCount * 100 -> Medal.Gold
            correctCount * 100 >= totalCount * 80 -> Medal.Silver
            correctCount * 100 >= totalCount * 60 -> Medal.Bronze
            else -> Medal.Star
        }
    }
}
