package com.vitantonio.nagauzzi.sansuukids.model

import com.vitantonio.nagauzzi.sansuukids.logic.AwardMedal

/**
 * クイズの進行状態を表すクラス。
 *
 * @property quiz 出題するクイズ
 * @property currentInput ユーザーが入力中の回答文字列
 * @property userAnswers ユーザーが回答済みの回答リスト
 */
internal data class QuizState(
    val quiz: Quiz,
    val currentInput: String = "",
    val userAnswers: List<UserAnswer> = emptyList()
) {
    val awardMedal: AwardMedal = AwardMedal()

    /**
     * 次に回答する問題のインデックス（0始まり）。
     * [userAnswers]のサイズから自動計算される。
     */
    val currentQuestionIndex: Int get() = userAnswers.size

    /**
     * 現在表示中の問題。
     * インデックスが範囲外の場合は[Question.None]を返す。
     */
    val currentQuestion: Question
        get() = quiz.questions.getOrElse(currentQuestionIndex) { Question.None }

    /**
     * クイズに含まれる全問題のリスト。
     */
    val totalQuestions: List<Question>
        get() = quiz.questions

    /**
     * クイズの進捗率（0.0〜1.0）。
     * 問題が存在しない場合は完了扱いとして1.0を返す。
     */
    val progress: Float
        get() {
            val totalQuestionsCount = totalQuestions.size
            return if (totalQuestionsCount == 0) {
                1.0f
            } else {
                currentQuestionIndex / totalQuestionsCount.toFloat()
            }
        }

    /**
     * 回答に桁を追加できるかどうか。
     * 正解の桁数を超える入力は許可しない（幼児〜小学生向けのヒントとして機能）。
     */
    val isAppendDigitEnabled: Boolean
        get() {
            val currentMathQuestion = currentQuestion as? Question.Math ?: return false
            val maxInputLength = currentMathQuestion.correctAnswer.toString().length
            return currentInput.length < maxInputLength
        }

    /**
     * 回答を送信できるかどうか。
     * 入力が空でない場合にtrueを返す。
     */
    val isSubmitEnabled: Boolean
        get() = currentInput.isNotEmpty()

    /**
     * クイズが完了したかどうか。
     * 全問題に回答済みの場合にtrueを返す。
     */
    val isQuizComplete: Boolean
        get() = userAnswers.size == totalQuestions.size

    /**
     * 回答済みの問題数。
     */
    val answeredCount: Int
        get() = userAnswers.size

    /**
     * 正解数。
     */
    val correctCount: Int
        get() = userAnswers.count { it.isCorrect }

    /**
     * 獲得したスコア。全問正解で100点。
     */
    val earnedScore: Int
        get() = ((correctCount.toFloat() / totalQuestions.size.toFloat()) * 100).toInt()

    /**
     * 正答率に応じて獲得したメダル。
     */
    val earnedMedal: Medal
        get() = awardMedal(isQuizComplete, correctCount, quiz.questions.size)
}
