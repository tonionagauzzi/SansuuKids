package com.vitantonio.nagauzzi.sansuukids.ui.component.quiz

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vitantonio.nagauzzi.sansuukids.model.Question

@Composable
internal fun AnswerCheckDialog(
    checkingAnswer: Boolean,
    currentQuestion: Question,
    currentInput: Int?,
    onDismiss: () -> Unit
) {
    if (checkingAnswer && currentQuestion is Question.Math && currentInput != null) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier.clickable { onDismiss() },
                shape = RoundedCornerShape(16.dp),
            ) {
                AnswerCheck(question = currentQuestion, answer = currentInput)
            }
        }
    }
}
