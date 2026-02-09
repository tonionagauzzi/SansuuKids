package com.vitantonio.nagauzzi.sansuukids.data

import com.vitantonio.nagauzzi.sansuukids.model.Level
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
     * @param operationType 演算タイプ
     * @param level 難易度レベル
     * @return カスタム出題範囲（カスタム値がない場合はデフォルト範囲）
     */
    fun getQuizRange(operationType: OperationType, level: Level): Flow<QuizRange>

    /**
     * カスタム出題範囲を設定する。
     *
     * @param quizRange カスタム出題範囲
     */
    suspend fun set(quizRange: QuizRange)

    /**
     * カスタム出題範囲をデフォルトにリセットする。
     *
     * @param operationType 演算タイプ
     * @param level 難易度レベル
     */
    suspend fun resetToDefault(operationType: OperationType, level: Level)
}
