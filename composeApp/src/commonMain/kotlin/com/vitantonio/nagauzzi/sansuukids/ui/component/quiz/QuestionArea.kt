package com.vitantonio.nagauzzi.sansuukids.ui.component.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.model.Question

@Composable
internal fun QuestionArea(
    currentQuestion: Question,
    currentInput: Int?,
    hintDisplayEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        val inputText = currentInput?.toString() ?: ""
        val displayText = "${currentQuestion.displayText} $inputText".trimEnd()

        Text(
            text = displayText,
            style = if (displayText.length <= 10) {
                // 一桁の計算なら大きめの文字サイズで表示（最大で"a x b = cd"の10文字）
                MaterialTheme.typography.displayLarge
            } else {
                MaterialTheme.typography.displayMedium
            },
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 8.dp)
                .testTag("question_text")
        )

        if (hintDisplayEnabled && currentQuestion is Question.Math) {
            HintArea(
                question = currentQuestion,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("hint_area")
            )
        }
    }
}
