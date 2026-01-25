package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vitantonio.nagauzzi.sansuukids.model.Question
import com.vitantonio.nagauzzi.sansuukids.model.QuizState
import com.vitantonio.nagauzzi.sansuukids.ui.component.AnswerCheck
import com.vitantonio.nagauzzi.sansuukids.ui.component.NumberKeypad
import com.vitantonio.nagauzzi.sansuukids.ui.component.QuizProgressBar
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.arrow_back
import sansuukids.composeapp.generated.resources.quiz_back

@Composable
internal fun QuizScreen(
    quizState: QuizState,
    perQuestionAnswerCheckEnabled: Boolean,
    onDigitClick: (Int) -> Unit,
    onDeleteClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeContentPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header: Cancel button and Progress
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onBackClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                ),
                modifier = Modifier
                    .height(64.dp)
                    .testTag("cancel_button"),
                shape = RoundedCornerShape(12.dp),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.arrow_back),
                    contentDescription = stringResource(Res.string.quiz_back),
                    modifier = Modifier.size(32.dp)
                )
            }

            QuizProgressBar(
                currentQuestion = quizState.currentQuestionIndex + 1,
                totalQuestions = quizState.totalQuestions.size,
                progress = quizState.progress,
                modifier = Modifier.width(150.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Question display area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Question text
            Text(
                text = quizState.currentQuestion.displayText,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.testTag("question_text")
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Input display
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(80.dp),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.testTag("input_display")
                ) {
                    val currentInputText = if (quizState.currentInput == null) {
                        ""
                    } else {
                        quizState.currentInput.toString()
                    }
                    Text(
                        text = currentInputText,
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        var checkingAnswer by remember { mutableStateOf(false) }

        // Number keypad
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
            isSubmitEnabled = quizState.isSubmitEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Answer check dialog
        val currentQuestion = quizState.currentQuestion
        val currentInput = quizState.currentInput
        if (checkingAnswer && currentQuestion is Question.Math && currentInput != null) {
            Dialog(
                onDismissRequest = {
                    checkingAnswer = false
                    onSubmitClick()
                }
            ) {
                Card(
                    modifier = Modifier
                        .clickable {
                            checkingAnswer = false
                            onSubmitClick()
                        },
                    shape = RoundedCornerShape(16.dp),
                ) {
                    AnswerCheck(
                        question = currentQuestion,
                        answer = currentInput
                    )
                }
            }
        }
    }
}
