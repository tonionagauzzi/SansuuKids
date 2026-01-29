package com.vitantonio.nagauzzi.sansuukids.ui.component.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.model.QuizState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.arrow_back
import sansuukids.composeapp.generated.resources.quiz_back

@Composable
internal fun QuizHeader(
    quizState: QuizState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
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
            modifier = Modifier.width(150.dp).padding(end = 16.dp)
        )
    }
}
