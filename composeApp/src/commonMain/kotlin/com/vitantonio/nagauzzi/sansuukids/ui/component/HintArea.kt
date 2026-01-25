package com.vitantonio.nagauzzi.sansuukids.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.model.Question
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random

private val fruitEmojis = listOf("🍎", "🍊", "🍋", "🍇", "🍓", "🍑", "🍒", "🍌", "🥝", "🍐")

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun HintArea(
    question: Question.Math,
    modifier: Modifier = Modifier
) {
    val (leftCount, rightCount) = when (question) {
        is Question.Math.Addition -> question.leftOperand to question.rightOperand
        is Question.Math.Subtraction -> question.leftOperand to question.rightOperand
        is Question.Math.Multiplication -> question.leftOperand to question.rightOperand
        is Question.Math.Division -> question.dividend to question.divisor
    }

    val fruitEmoji = remember(question) {
        fruitEmojis.shuffled(Random).first()
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left operand hint
        FlowRow(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shape = RoundedCornerShape(16.dp)
                )
                .fillMaxHeight()
                .padding(16.dp)
                .weight(1f)
                .testTag("hint_left"),
            horizontalArrangement = Arrangement.Start,
            verticalArrangement = Arrangement.Top
        ) {
            repeat(leftCount) {
                Text(
                    text = fruitEmoji,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Right operand hint
        FlowRow(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shape = RoundedCornerShape(16.dp)
                )
                .fillMaxHeight()
                .padding(16.dp)
                .weight(1f)
                .testTag("hint_right"),
            horizontalArrangement = Arrangement.Start,
            verticalArrangement = Arrangement.Top
        ) {
            repeat(rightCount) {
                Text(
                    text = fruitEmoji,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
private fun HintAreaPreviewAddition() {
    SansuuKidsTheme {
        HintArea(
            question = Question.Math.Addition(leftOperand = 3, rightOperand = 5)
        )
    }
}

@Preview
@Composable
private fun HintAreaPreviewSubtraction() {
    SansuuKidsTheme {
        HintArea(
            question = Question.Math.Subtraction(leftOperand = 7, rightOperand = 2)
        )
    }
}
