package com.vitantonio.nagauzzi.sansuukids.data

import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import kotlinx.coroutines.flow.Flow

/**
 * 難易度カスタム設定を永続化するリポジトリのインターフェース。
 */
internal interface DifficultyRepository {
    /**
     * 指定されたモードとレベルのカスタム出題範囲を取得する。
     *
     * @param mode 計算モード
     * @param level 難易度レベル
     * @return カスタム出題範囲のリスト（カスタム値がない場合はデフォルト範囲）
     */
    fun getCustomRanges(mode: Mode, level: Level): Flow<List<QuizRange>>

    /**
     * カスタム出題範囲を設定する。
     *
     * @param operationType 演算タイプ
     * @param level 難易度レベル
     * @param min 最小値
     * @param max 最大値
     */
    suspend fun setCustomRange(operationType: OperationType, level: Level, min: Int, max: Int)

    /**
     * カスタム出題範囲をデフォルトにリセットする。
     *
     * @param operationType 演算タイプ
     * @param level 難易度レベル
     */
    suspend fun resetToDefault(operationType: OperationType, level: Level)
}
