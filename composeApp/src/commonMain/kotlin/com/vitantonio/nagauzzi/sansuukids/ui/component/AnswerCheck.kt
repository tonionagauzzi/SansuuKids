package com.vitantonio.nagauzzi.sansuukids.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.model.Question
import org.jetbrains.compose.resources.stringResource
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.answer_check_correct
import sansuukids.composeapp.generated.resources.answer_check_incorrect
import sansuukids.composeapp.generated.resources.answer_check_your_answer

@Composable
internal fun AnswerCheck(
    question: Question.Math,
    answer: Int
) {
    val isCorrect = answer == question.correctAnswer
    val accentColor = if (isCorrect) {
        MaterialTheme.colorScheme.onBackground
    } else {
        MaterialTheme.colorScheme.error
    }
    val backgroundColor = if (isCorrect) {
        MaterialTheme.colorScheme.background
    } else {
        MaterialTheme.colorScheme.errorContainer
    }

    Column(
        modifier = Modifier
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
        val questionWithAnswer = question.displayText.replace(
            "?",
            question.correctAnswer.toString()
        )
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
                    text = stringResource(Res.string.answer_check_your_answer, answer),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = accentColor,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
