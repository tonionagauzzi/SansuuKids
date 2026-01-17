package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.model.Question
import com.vitantonio.nagauzzi.sansuukids.model.UserAnswer
import com.vitantonio.nagauzzi.sansuukids.ui.component.LargeButton
import com.vitantonio.nagauzzi.sansuukids.ui.component.QuizProgressBar
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.answer_check_back
import sansuukids.composeapp.generated.resources.answer_check_correct
import sansuukids.composeapp.generated.resources.answer_check_finish
import sansuukids.composeapp.generated.resources.answer_check_incorrect
import sansuukids.composeapp.generated.resources.answer_check_next
import sansuukids.composeapp.generated.resources.answer_check_previous
import sansuukids.composeapp.generated.resources.answer_check_your_answer

@Composable
internal fun AnswerCheckScreen(
    questions: List<Question>,
    userAnswers: List<UserAnswer>,
    onFinishClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentIndex by rememberSaveable { mutableIntStateOf(0) }

    val currentQuestion = questions.getOrNull(currentIndex) ?: return
    val currentUserAnswer = userAnswers.getOrNull(currentIndex) ?: return
    val isCorrect = currentUserAnswer.isCorrect
    val isFirstQuestion = currentIndex == 0
    val isLastQuestion = currentIndex == questions.size - 1

    val backgroundColor = if (isCorrect) {
        MaterialTheme.colorScheme.background
    } else {
        MaterialTheme.colorScheme.errorContainer
    }

    val accentColor = if (isCorrect) {
        MaterialTheme.colorScheme.onBackground
    } else {
        MaterialTheme.colorScheme.onErrorContainer
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
            // Result indicator
            Text(
                text = if (isCorrect) {
                    stringResource(Res.string.answer_check_correct)
                } else {
                    stringResource(Res.string.answer_check_incorrect)
                },
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = accentColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.testTag("result_indicator")
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Question text with correct answer
            val questionWithAnswer = when (currentQuestion) {
                is Question.Math -> currentQuestion.displayText.replace(
                    "?",
                    currentQuestion.correctAnswer.toString()
                )

                is Question.None -> currentQuestion.displayText
            }
            Text(
                text = questionWithAnswer,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.testTag("question_text")
            )

            Spacer(modifier = Modifier.height(24.dp))

            // User's answer display
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(80.dp),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.testTag("user_answer_display")
                ) {
                    Text(
                        text = stringResource(
                            Res.string.answer_check_your_answer,
                            currentUserAnswer.answer
                        ),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = accentColor,
                        textAlign = TextAlign.Center
                    )
                }
            }
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
                onClick = if (isFirstQuestion) {
                    { onFinishClick() }
                } else {
                    { currentIndex-- }
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
            onFinishClick = {}
        )
    }
}
