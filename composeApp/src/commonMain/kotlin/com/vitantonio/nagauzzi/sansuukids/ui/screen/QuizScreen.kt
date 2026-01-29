package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.model.Question
import com.vitantonio.nagauzzi.sansuukids.model.Quiz
import com.vitantonio.nagauzzi.sansuukids.model.QuizState
import com.vitantonio.nagauzzi.sansuukids.ui.component.quiz.AnswerArea
import com.vitantonio.nagauzzi.sansuukids.ui.component.quiz.QuestionArea
import com.vitantonio.nagauzzi.sansuukids.ui.component.quiz.QuizHeader
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun QuizScreen(
    quizState: QuizState,
    perQuestionAnswerCheckEnabled: Boolean,
    hintDisplayEnabled: Boolean,
    onDigitClick: (Int) -> Unit,
    onDeleteClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
    ) {
        if (maxWidth > maxHeight) {
            QuizScreenLandscape(
                quizState = quizState,
                perQuestionAnswerCheckEnabled = perQuestionAnswerCheckEnabled,
                hintDisplayEnabled = hintDisplayEnabled,
                onDigitClick = onDigitClick,
                onDeleteClick = onDeleteClick,
                onSubmitClick = onSubmitClick,
                onBackClick = onBackClick
            )
        } else {
            QuizScreenPortrait(
                quizState = quizState,
                perQuestionAnswerCheckEnabled = perQuestionAnswerCheckEnabled,
                hintDisplayEnabled = hintDisplayEnabled,
                onDigitClick = onDigitClick,
                onDeleteClick = onDeleteClick,
                onSubmitClick = onSubmitClick,
                onBackClick = onBackClick
            )
        }
    }
}

@Composable
private fun QuizScreenPortrait(
    quizState: QuizState,
    perQuestionAnswerCheckEnabled: Boolean,
    hintDisplayEnabled: Boolean,
    onDigitClick: (Int) -> Unit,
    onDeleteClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        QuizHeader(quizState = quizState, onBackClick = onBackClick)

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

@Composable
private fun QuizScreenLandscape(
    quizState: QuizState,
    perQuestionAnswerCheckEnabled: Boolean,
    hintDisplayEnabled: Boolean,
    onDigitClick: (Int) -> Unit,
    onDeleteClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side: Header and Question area
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            QuizHeader(quizState = quizState, onBackClick = onBackClick)

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
}

@Preview(widthDp = 360, heightDp = 640) // 縦画面
@Preview(widthDp = 640, heightDp = 360) // 横画面
@Preview(widthDp = 480, heightDp = 480) // 正方形画面
@Preview(widthDp = 481, heightDp = 480) // 僅かに横画面
@Composable
private fun QuizScreenPreview() {
    SansuuKidsTheme {
        QuizScreen(
            quizState = QuizState(
                quiz = Quiz(
                    questions = listOf(
                        Question.Math.Addition(3, 5),
                        Question.Math.Subtraction(10, 4),
                        Question.Math.Multiplication(2, 6)
                    ),
                    mode = Mode.ADDITION,
                    level = Level.EASY
                )
            ),
            perQuestionAnswerCheckEnabled = true,
            hintDisplayEnabled = true,
            onDigitClick = {},
            onDeleteClick = {},
            onSubmitClick = {},
            onBackClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}
