package com.vitantonio.nagauzzi.sansuukids.model

internal data class QuizState(
    val quiz: Quiz,
    val currentQuestionIndex: Int = 0,
    val currentInput: String = "",
    val userAnswers: List<UserAnswer> = emptyList()
) {
    val currentQuestion: Question
        get() = quiz.questions.getOrElse(currentQuestionIndex) { Question.None }

    val totalQuestions: List<Question>
        get() = quiz.questions

    val progress: Float
        get() = (currentQuestionIndex + 1) / quiz.questions.size.toFloat()

    // 正解より大きな桁数の回答を入力できないことはプレイヤー（幼児〜小学生）へのヒントとして許容する
    val isAppendDigitEnabled: Boolean
        get() {
            val currentMathQuestion = currentQuestion as? Question.Math ?: return false
            val maxInputLength = currentMathQuestion.correctAnswer.toString().length
            return currentInput.length < maxInputLength
        }

    val isSubmitEnabled: Boolean
        get() = currentInput.isNotEmpty()

    val isQuizComplete: Boolean
        get() = userAnswers.size == quiz.questions.size

    val answeredCount: Int
        get() = userAnswers.size

    fun toResult(): QuizResult = QuizResult(quiz, userAnswers)
}
