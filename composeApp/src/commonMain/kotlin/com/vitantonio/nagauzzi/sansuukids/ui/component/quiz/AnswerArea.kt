package com.vitantonio.nagauzzi.sansuukids.ui.component.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.model.Question
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun AnswerArea(
    currentQuestion: Question,
    currentInput: Int?,
    isSubmitEnabled: Boolean,
    perQuestionAnswerCheckEnabled: Boolean,
    onDigitClick: (Int) -> Unit,
    onDeleteClick: () -> Unit,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var checkingAnswer by remember { mutableStateOf(false) }

        NumberKeypad(
            onDigitClick = onDigitClick,
            onDeleteClick = onDeleteClick,
            onSubmitClick = {
                if (perQuestionAnswerCheckEnabled) {
                    checkingAnswer = true
                } else {
                    onSubmitClick()
                }
            },
            isSubmitEnabled = isSubmitEnabled
        )

        AnswerCheckDialog(
            checkingAnswer = checkingAnswer,
            currentQuestion = currentQuestion,
            currentInput = currentInput,
            onDismiss = {
                checkingAnswer = false
                onSubmitClick()
            }
        )
    }
}

@Preview
@Composable
private fun AnswerAreaPreview() {
    SansuuKidsTheme {
        AnswerArea(
            currentQuestion = Question.Math.Addition(leftOperand = 1, rightOperand = 2),
            currentInput = 3,
            isSubmitEnabled = true,
            perQuestionAnswerCheckEnabled = true,
            onDigitClick = {},
            onDeleteClick = {},
            onSubmitClick = {}
        )
    }
}
