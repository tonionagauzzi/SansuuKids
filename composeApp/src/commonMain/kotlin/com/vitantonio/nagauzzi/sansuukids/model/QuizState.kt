package com.vitantonio.nagauzzi.sansuukids.model

/**
 * クイズの進行状態を表すクラス。
 *
 * @property quiz 出題するクイズ
 * @property currentQuestionIndex 現在の問題番号（0始まり）
 * @property currentInput ユーザーが入力中の回答文字列
 * @property userAnswers ユーザーが回答済みの回答リスト
 */
internal data class QuizState(
    val quiz: Quiz,
    val currentQuestionIndex: Int = 0,
    val currentInput: String = "",
    val userAnswers: List<UserAnswer> = emptyList()
) {
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
            val totalQuestionsCount = quiz.questions.size
            return if (totalQuestionsCount == 0) {
                1.0f
            } else {
                (currentQuestionIndex + 1) / totalQuestionsCount.toFloat()
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
        get() = userAnswers.size == quiz.questions.size

    /**
     * 回答済みの問題数。
     */
    val answeredCount: Int
        get() = userAnswers.size

    fun toResult(): QuizResult = QuizResult(quiz, userAnswers)
}
