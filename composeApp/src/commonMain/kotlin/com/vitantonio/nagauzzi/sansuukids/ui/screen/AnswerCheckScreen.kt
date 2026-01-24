package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.model.Question
import com.vitantonio.nagauzzi.sansuukids.model.UserAnswer
import com.vitantonio.nagauzzi.sansuukids.ui.component.AnswerCheck
import com.vitantonio.nagauzzi.sansuukids.ui.component.LargeButton
import com.vitantonio.nagauzzi.sansuukids.ui.component.QuizProgressBar
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.answer_check_back
import sansuukids.composeapp.generated.resources.answer_check_finish
import sansuukids.composeapp.generated.resources.answer_check_next
import sansuukids.composeapp.generated.resources.answer_check_previous

@Composable
internal fun AnswerCheckScreen(
    questions: List<Question>,
    userAnswers: List<UserAnswer>,
    onBackClick: () -> Unit,
    onFinishClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentIndex by rememberSaveable { mutableIntStateOf(0) }

    val currentQuestion = questions.getOrNull(currentIndex) ?: return
    val currentUserAnswer = userAnswers.getOrNull(currentIndex) ?: return
    val isFirstQuestion = currentIndex == 0
    val isLastQuestion = currentIndex == questions.size - 1

    val isCorrect = currentUserAnswer.isCorrect
    val backgroundColor = if (isCorrect) {
        MaterialTheme.colorScheme.background
    } else {
        MaterialTheme.colorScheme.errorContainer
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .safeContentPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header: Progress
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            QuizProgressBar(
                currentQuestion = currentIndex + 1,
                totalQuestions = questions.size,
                progress = (currentIndex + 1) / questions.size.toFloat(),
                modifier = Modifier.width(150.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Question and Answer display area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnswerCheck(
                question = currentQuestion,
                answer = currentUserAnswer.answer
            )
        }

        // Navigation buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            LargeButton(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                text = if (isFirstQuestion) {
                    stringResource(Res.string.answer_check_back)
                } else {
                    stringResource(Res.string.answer_check_previous)
                },
                textStyle = MaterialTheme.typography.headlineSmall,
                onClick = {
                    if (isFirstQuestion) {
                        onBackClick()
                    } else {
                        currentIndex--
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .testTag(
                        if (isFirstQuestion) {
                            "back_button"
                        } else {
                            "previous_button"
                        }
                    )
            )

            LargeButton(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                text = if (isLastQuestion) {
                    stringResource(Res.string.answer_check_finish)
                } else {
                    stringResource(Res.string.answer_check_next)
                },
                textStyle = MaterialTheme.typography.headlineSmall,
                onClick = {
                    if (isLastQuestion) {
                        onFinishClick()
                    } else {
                        currentIndex++
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .testTag(
                        if (isLastQuestion) {
                            "finish_button"
                        } else {
                            "next_button"
                        }
                    )
            )
        }
    }
}

@Preview
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
