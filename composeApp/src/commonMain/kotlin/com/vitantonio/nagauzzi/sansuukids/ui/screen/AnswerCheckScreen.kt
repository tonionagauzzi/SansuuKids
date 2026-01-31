package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vitantonio.nagauzzi.sansuukids.model.Question
import com.vitantonio.nagauzzi.sansuukids.model.UserAnswer
import com.vitantonio.nagauzzi.sansuukids.ui.component.ProgressHeader
import com.vitantonio.nagauzzi.sansuukids.ui.component.answercheck.AnswerCheckContent
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun AnswerCheckScreen(
    questions: List<Question>,
    userAnswers: List<UserAnswer>,
    onBackClick: () -> Unit,
    onFinishClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentIndex by rememberSaveable { mutableIntStateOf(0) }
    val currentUserAnswer = userAnswers.getOrNull(currentIndex) ?: return
    val backgroundColor = if (currentUserAnswer.isCorrect) {
        MaterialTheme.colorScheme.background
    } else {
        MaterialTheme.colorScheme.errorContainer
    }

    BoxWithConstraints(
        modifier = modifier
            .background(backgroundColor)
            .safeContentPadding()
            .fillMaxSize()
    ) {
        val isLandscape = maxWidth > maxHeight

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ProgressHeader(
                currentQuestionIndex = currentIndex + 1,
                totalQuestionsSize = questions.size,
                progress = (currentIndex + 1) / questions.size.toFloat(),
                onBackClick = onBackClick
            )

            AnswerCheckContent(
                isLandscape = isLandscape,
                currentIndex = currentIndex,
                totalQuestions = questions.size,
                currentQuestion = questions.getOrNull(currentIndex) as? Question.Math
                    ?: return@BoxWithConstraints,
                currentUserAnswer = currentUserAnswer,
                isFirstQuestion = currentIndex == 0,
                isLastQuestion = currentIndex == questions.size - 1,
                onBackClick = onBackClick,
                onFinishClick = onFinishClick,
                onPreviousClick = { currentIndex-- },
                onNextClick = { currentIndex++ }
            )
        }
    }
}

@Preview(widthDp = 360, heightDp = 640) // 縦画面
@Preview(widthDp = 640, heightDp = 360) // 横画面
@Preview(widthDp = 480, heightDp = 480) // 正方形画面
@Preview(widthDp = 481, heightDp = 480) // 僅かに横画面
@Composable
private fun AnswerCheckScreenCorrectPreview() {
    SansuuKidsTheme {
        AnswerCheckScreen(
            questions = listOf(Question.Math.Addition(3, 5)),
            userAnswers = listOf(UserAnswer(0, 8, true)),
            onBackClick = {},
            onFinishClick = {}
        )
    }
}

@Preview
@Composable
private fun AnswerCheckScreenIncorrectPreview() {
    SansuuKidsTheme {
        AnswerCheckScreen(
            questions = listOf(Question.Math.Addition(3, 5)),
            userAnswers = listOf(UserAnswer(0, 7, false)),
            onBackClick = {},
            onFinishClick = {}
        )
    }
}
