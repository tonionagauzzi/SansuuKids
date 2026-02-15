package com.vitantonio.nagauzzi.sansuukids.ui.component.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Division
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Multiplication
import com.vitantonio.nagauzzi.sansuukids.model.Question.Math.Subtraction

private val fruitEmojis = listOf("🍎", "🍊", "🍋", "🍇", "🍓", "🍑", "🍈", "🍌", "🥝", "🍐")

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

        // 掛け算の場合、左オペランド列×右オペランド行のグリッドで表示
        is Multiplication -> GridHint(
            columns = question.leftOperand,
            rows = question.rightOperand,
            fruitEmoji = fruitEmoji,
            modifier = modifier,
            testTag = "hint_multiplication",
        )

        // 割り算の場合、答え列×除数行のグリッドで表示し、2行目以降を強調色にする
        is Division -> GridHint(
            columns = question.correctAnswer,
            rows = question.divisor,
            fruitEmoji = fruitEmoji,
            modifier = modifier,
            testTag = "hint_division",
            rowHighlight = { it > 0 },
        )
    }
}

@Composable
private fun GridHint(
    columns: Int,
    rows: Int,
    fruitEmoji: String,
    modifier: Modifier = Modifier,
    testTag: String,
    rowHighlight: (Int) -> Boolean = { false },
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .testTag(testTag)
        ) {
            repeat(rows) { rowIndex ->
                Row(
                    modifier = if (rowHighlight(rowIndex)) {
                        Modifier.background(color = MaterialTheme.colorScheme.errorContainer)
                    } else {
                        Modifier
                    }
                ) {
                    repeat(columns) {
                        Text(
                            text = fruitEmoji,
                            style = when {
                                columns <= 10 -> MaterialTheme.typography.bodyLarge
                                columns <= 15 -> MaterialTheme.typography.bodyMedium
                                else -> MaterialTheme.typography.bodySmall
                            },
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
