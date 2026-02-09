package com.vitantonio.nagauzzi.sansuukids.ui.component.levelselection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.logic.levelselection.stepQuizRange
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import com.vitantonio.nagauzzi.sansuukids.model.getSliderMaxBound
import com.vitantonio.nagauzzi.sansuukids.model.getSliderMinBound
import com.vitantonio.nagauzzi.sansuukids.model.labelRes
import org.jetbrains.compose.resources.stringResource
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.difficulty_medal_disabled
import sansuukids.composeapp.generated.resources.difficulty_range_label

@Composable
internal fun DifficultyAdjustmentContent(
    quizRange: QuizRange,
    onQuizRangeChanged: (newRange: QuizRange) -> Unit,
    modifier: Modifier = Modifier
) {
    var draggingRange by remember { mutableStateOf<QuizRange?>(null) }
    val displayRange = draggingRange ?: quizRange
    val defaultRange = QuizRange.Default(quizRange.operationType, quizRange.level)

    // 外部からの変更（リセット、永続化完了等）時にドラッグ状態をクリア
    LaunchedEffect(quizRange) {
        draggingRange = null
    }

    Column(
        modifier = modifier
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OperationRangeSlider(
            quizRange = displayRange,
            medalDisabled = displayRange.min < defaultRange.min ||
                    displayRange.max < defaultRange.max,
            onQuizRangeChanging = { newRange -> draggingRange = newRange },
            onQuizRangeChangeFinished = {
                draggingRange?.let { newRange ->
                    if (newRange.min != quizRange.min || newRange.max != quizRange.max) {
                        onQuizRangeChanged(newRange)
                    }
                }
            }
        )
    }
}

@Composable
private fun OperationRangeSlider(
    quizRange: QuizRange,
    medalDisabled: Boolean,
    onQuizRangeChanging: (newRange: QuizRange) -> Unit,
    onQuizRangeChangeFinished: () -> Unit
) {
    val tag = quizRange.operationType.name.lowercase()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(
                Res.string.difficulty_range_label,
                stringResource(quizRange.operationType.labelRes),
                quizRange.min,
                quizRange.max
            ),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.testTag("difficulty_label_$tag")
        )

        val minimumValue = quizRange.operationType.getSliderMinBound(level = quizRange.level)
        val maximumValue = quizRange.operationType.getSliderMaxBound(level = quizRange.level)
        RangeSlider(
            value = quizRange.min.toFloat()..quizRange.max.toFloat(),
            onValueChange = { range ->
                // スライダーが表示中の値をプログラム的な値に補正して新しい最小値および最大値とする
                onQuizRangeChanging(
                    stepQuizRange(
                        quizRange = quizRange,
                        newMin = range.start,
                        newMax = range.endInclusive
                    )
                )
            },
            onValueChangeFinished = onQuizRangeChangeFinished,
            valueRange = 1f..maximumValue.toFloat(),
            steps = maxOf(0, (maximumValue / minimumValue) - 1),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("difficulty_slider_$tag")
        )

        if (medalDisabled) {
            Text(
                text = stringResource(Res.string.difficulty_medal_disabled),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.testTag("difficulty_warning_$tag")
            )
        }
    }
}
