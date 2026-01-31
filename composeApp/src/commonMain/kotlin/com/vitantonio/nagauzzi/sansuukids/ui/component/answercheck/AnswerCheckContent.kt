package com.vitantonio.nagauzzi.sansuukids.ui.component.answercheck

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.model.Question
import com.vitantonio.nagauzzi.sansuukids.model.UserAnswer
import com.vitantonio.nagauzzi.sansuukids.ui.component.LargeButton
import com.vitantonio.nagauzzi.sansuukids.ui.component.quiz.AnswerCheck
import org.jetbrains.compose.resources.stringResource
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.answer_check_back
import sansuukids.composeapp.generated.resources.answer_check_finish
import sansuukids.composeapp.generated.resources.answer_check_next
import sansuukids.composeapp.generated.resources.answer_check_previous

@Composable
internal fun AnswerCheckContent(
    isLandscape: Boolean,
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
    if (isLandscape) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnswerCheck(
                question = currentQuestion,
                answer = currentUserAnswer.answer,
                modifier = Modifier.weight(1f)
            )

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
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
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
}

private data class AnswerCheckButtonConfig(
    val containerColor: Color,
    val contentColor: Color,
    val text: String,
    val onClick: () -> Unit,
    val testTag: String
)

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
