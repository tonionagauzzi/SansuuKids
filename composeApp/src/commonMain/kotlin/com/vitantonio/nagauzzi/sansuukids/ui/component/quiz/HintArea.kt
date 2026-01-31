package com.vitantonio.nagauzzi.sansuukids.ui.component.quiz

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
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Addition
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Subtraction
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

private val fruitEmojis = listOf("🍎", "🍊", "🍋", "🍇", "🍓", "🍑", "🍒", "🍌", "🥝", "🍐")

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun HintArea(
    question: Math,
    modifier: Modifier = Modifier,
    fruitEmoji: String = remember(question) {
        fruitEmojis.shuffled().first()
    }
) {
    when (question) {
        // 足し算の場合、2つの数を別々のエリアに表示
        is Addition -> Row(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FlowRow(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainerLowest,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .fillMaxHeight()
                    .padding(16.dp)
                    .weight(1f)
                    .testTag("hint_addition_left"),
                horizontalArrangement = Arrangement.Start,
                verticalArrangement = Arrangement.Top
            ) {
                repeat(question.leftOperand) {
                    Text(
                        text = fruitEmoji,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            FlowRow(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainerLowest,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .fillMaxHeight()
                    .padding(16.dp)
                    .weight(1f)
                    .testTag("hint_addition_right"),
                horizontalArrangement = Arrangement.Start,
                verticalArrangement = Arrangement.Top
            ) {
                repeat(question.rightOperand) {
                    Text(
                        text = fruitEmoji,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // 引き算の場合、1つのエリアに表示し、引く数ぶんの絵文字に強調色をつける
        is Subtraction -> FlowRow(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
                .testTag("hint_subtraction"),
            horizontalArrangement = Arrangement.Start,
            verticalArrangement = Arrangement.Top
        ) {
            repeat(question.correctAnswer) {
                Text(
                    text = fruitEmoji,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
            }
            repeat(question.rightOperand) {
                Text(
                    text = fruitEmoji,
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.errorContainer),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
            }
        }

        // 他のタイプはヒントなし
        else -> {}
    }
}

@Preview
@Composable
private fun HintAreaPreviewAddition() {
    SansuuKidsTheme {
        HintArea(
            question = Addition(leftOperand = 3, rightOperand = 5),
            fruitEmoji = "🍎"
        )
    }
}

@Preview
@Composable
private fun HintAreaPreviewSubtraction() {
    SansuuKidsTheme {
        HintArea(
            question = Subtraction(leftOperand = 7, rightOperand = 2),
            fruitEmoji = "🍊"
        )
    }
}
