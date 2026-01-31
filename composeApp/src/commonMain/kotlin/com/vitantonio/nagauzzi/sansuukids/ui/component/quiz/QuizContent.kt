package com.vitantonio.nagauzzi.sansuukids.ui.component.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.model.QuizState

@Composable
internal fun QuizContent(
    isLandscape: Boolean,
    quizState: QuizState,
    perQuestionAnswerCheckEnabled: Boolean,
    hintDisplayEnabled: Boolean,
    onDigitClick: (Int) -> Unit,
    onDeleteClick: () -> Unit,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLandscape) {
        Row(
            modifier = modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                QuestionArea(
                    currentQuestion = quizState.currentQuestion,
                    currentInput = quizState.currentInput,
                    hintDisplayEnabled = hintDisplayEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }

            Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                AnswerArea(
                    currentQuestion = quizState.currentQuestion,
                    currentInput = quizState.currentInput,
                    isSubmitEnabled = quizState.isSubmitEnabled,
                    perQuestionAnswerCheckEnabled = perQuestionAnswerCheckEnabled,
                    onDigitClick = onDigitClick,
                    onDeleteClick = onDeleteClick,
                    onSubmitClick = onSubmitClick
                )
            }
        }
    } else {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                QuestionArea(
                    currentQuestion = quizState.currentQuestion,
                    currentInput = quizState.currentInput,
                    hintDisplayEnabled = hintDisplayEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

                AnswerArea(
                    currentQuestion = quizState.currentQuestion,
                    currentInput = quizState.currentInput,
                    isSubmitEnabled = quizState.isSubmitEnabled,
                    perQuestionAnswerCheckEnabled = perQuestionAnswerCheckEnabled,
                    onDigitClick = onDigitClick,
                    onDeleteClick = onDeleteClick,
                    onSubmitClick = onSubmitClick
                )
            }
        }
    }
}
