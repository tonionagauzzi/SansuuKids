package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import com.vitantonio.nagauzzi.sansuukids.model.Question
import com.vitantonio.nagauzzi.sansuukids.model.Quiz
import com.vitantonio.nagauzzi.sansuukids.model.QuizState
import com.vitantonio.nagauzzi.sansuukids.ui.component.quiz.QuizContent
import com.vitantonio.nagauzzi.sansuukids.ui.component.ProgressHeader
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
            .safeContentPadding()
            .fillMaxSize()
    ) {
        val isLandscape = maxWidth > maxHeight

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ProgressHeader(
                currentQuestionIndex = quizState.currentQuestionIndex + 1,
                totalQuestionsSize = quizState.quiz.questions.size,
                progress = quizState.progress,
                onBackClick = onBackClick
            )

            QuizContent(
                isLandscape = isLandscape,
                quizState = quizState,
                perQuestionAnswerCheckEnabled = perQuestionAnswerCheckEnabled,
                hintDisplayEnabled = hintDisplayEnabled,
                onDigitClick = onDigitClick,
                onDeleteClick = onDeleteClick,
                onSubmitClick = onSubmitClick,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
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
                    operationType = OperationType.Addition,
                    level = Level.Easy
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
