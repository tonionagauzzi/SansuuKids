package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.model.Question
import com.vitantonio.nagauzzi.sansuukids.model.UserAnswer
import com.vitantonio.nagauzzi.sansuukids.ui.component.quiz.AnswerCheck
import com.vitantonio.nagauzzi.sansuukids.ui.component.LargeButton
import com.vitantonio.nagauzzi.sansuukids.ui.component.quiz.QuizProgressBar
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.answer_check_back
import sansuukids.composeapp.generated.resources.answer_check_finish
import sansuukids.composeapp.generated.resources.answer_check_next
import sansuukids.composeapp.generated.resources.answer_check_previous

private data class AnswerCheckButtonConfig(
    val containerColor: Color,
    val contentColor: Color,
    val text: String,
    val onClick: () -> Unit,
    val testTag: String
)

@Composable
internal fun AnswerCheckScreen(
    questions: List<Question>,
    userAnswers: List<UserAnswer>,
    onBackClick: () -> Unit,
    onFinishClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentIndex by rememberSaveable { mutableIntStateOf(0) }

    val currentQuestion = questions.getOrNull(currentIndex) as? Question.Math ?: return
    val currentUserAnswer = userAnswers.getOrNull(currentIndex) ?: return
    val isFirstQuestion = currentIndex == 0
    val isLastQuestion = currentIndex == questions.size - 1

    val isCorrect = currentUserAnswer.isCorrect
    val backgroundColor = if (isCorrect) {
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
        if (maxWidth > maxHeight) {
            AnswerCheckScreenLandscape(
                currentIndex = currentIndex,
                totalQuestions = questions.size,
                currentQuestion = currentQuestion,
                currentUserAnswer = currentUserAnswer,
                isFirstQuestion = isFirstQuestion,
                isLastQuestion = isLastQuestion,
                onBackClick = onBackClick,
                onFinishClick = onFinishClick,
                onPreviousClick = { currentIndex-- },
                onNextClick = { currentIndex++ }
            )
        } else {
            AnswerCheckScreenPortrait(
                currentIndex = currentIndex,
                totalQuestions = questions.size,
                currentQuestion = currentQuestion,
                currentUserAnswer = currentUserAnswer,
                isFirstQuestion = isFirstQuestion,
                isLastQuestion = isLastQuestion,
                onBackClick = onBackClick,
                onFinishClick = onFinishClick,
                onPreviousClick = { currentIndex-- },
                onNextClick = { currentIndex++ }
            )
        }
    }
}

@Composable
private fun AnswerCheckScreenPortrait(
    currentIndex: Int,
    totalQuestions: Int,
    currentQuestion: Question.Math,
    currentUserAnswer: UserAnswer,
    isFirstQuestion: Boolean,
    isLastQuestion: Boolean,
    onBackClick: () -> Unit,
    onFinishClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        QuizProgressBar(
            currentQuestion = currentIndex + 1,
            totalQuestions = totalQuestions,
            progress = (currentIndex + 1) / totalQuestions.toFloat(),
            modifier = Modifier.width(150.dp)
        )

        AnswerCheck(
            question = currentQuestion,
            answer = currentUserAnswer.answer,
            modifier = Modifier.weight(1f)
        )

        AnswerCheckButtons(
            isLandscape = false,
            isFirstQuestion = isFirstQuestion,
            isLastQuestion = isLastQuestion,
            onBackClick = onBackClick,
            onFinishClick = onFinishClick,
            onPreviousClick = onPreviousClick,
            onNextClick = onNextClick
        )
    }
}

@Composable
private fun AnswerCheckScreenLandscape(
    currentIndex: Int,
    totalQuestions: Int,
    currentQuestion: Question.Math,
    currentUserAnswer: UserAnswer,
    isFirstQuestion: Boolean,
    isLastQuestion: Boolean,
    onBackClick: () -> Unit,
    onFinishClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            QuizProgressBar(
                currentQuestion = currentIndex + 1,
                totalQuestions = totalQuestions,
                progress = (currentIndex + 1) / totalQuestions.toFloat(),
                modifier = Modifier.width(150.dp)
            )

            AnswerCheck(
                question = currentQuestion,
                answer = currentUserAnswer.answer,
                modifier = Modifier.weight(1f)
            )
        }

        AnswerCheckButtons(
            isLandscape = true,
            isFirstQuestion = isFirstQuestion,
            isLastQuestion = isLastQuestion,
            onBackClick = onBackClick,
            onFinishClick = onFinishClick,
            onPreviousClick = onPreviousClick,
            onNextClick = onNextClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun AnswerCheckButtons(
    isLandscape: Boolean,
    isFirstQuestion: Boolean,
    isLastQuestion: Boolean,
    onBackClick: () -> Unit,
    onFinishClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttons = listOf(
        AnswerCheckButtonConfig(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            text = if (isFirstQuestion) stringResource(Res.string.answer_check_back)
                else stringResource(Res.string.answer_check_previous),
            onClick = { if (isFirstQuestion) onBackClick() else onPreviousClick() },
            testTag = if (isFirstQuestion) "back_button" else "previous_button"
        ),
        AnswerCheckButtonConfig(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            text = if (isLastQuestion) stringResource(Res.string.answer_check_finish)
                else stringResource(Res.string.answer_check_next),
            onClick = { if (isLastQuestion) onFinishClick() else onNextClick() },
            testTag = if (isLastQuestion) "finish_button" else "next_button"
        )
    )

    if (isLandscape) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            buttons.forEach { config ->
                LargeButton(
                    containerColor = config.containerColor,
                    contentColor = config.contentColor,
                    text = config.text,
                    textStyle = MaterialTheme.typography.headlineSmall,
                    onClick = config.onClick,
                    modifier = Modifier
                        .height(56.dp)
                        .testTag(config.testTag)
                )
            }
        }
    } else {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            buttons.forEach { config ->
                LargeButton(
                    containerColor = config.containerColor,
                    contentColor = config.contentColor,
                    text = config.text,
                    textStyle = MaterialTheme.typography.headlineSmall,
                    onClick = config.onClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .testTag(config.testTag)
                )
            }
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
