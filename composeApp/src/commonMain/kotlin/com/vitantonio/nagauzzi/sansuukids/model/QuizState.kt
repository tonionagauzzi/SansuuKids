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

    val isAppendDigitEnabled: Boolean
        get() {
            val maxInputLength = currentQuestion.correctAnswer.toString().length
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
