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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import com.vitantonio.nagauzzi.sansuukids.model.labelRes
import org.jetbrains.compose.resources.stringResource
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.difficulty_medal_disabled
import sansuukids.composeapp.generated.resources.difficulty_range_label

private const val SLIDER_MIN_VALUE = 1

@Composable
internal fun DifficultyAdjustmentContent(
    level: Level,
    operationType: OperationType,
    quizRange: QuizRange,
    onRangeChanged: (OperationType, Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OperationRangeSlider(
            operationType = operationType,
            currentMin = quizRange.min,
            currentMax = quizRange.max,
            sliderMax = getSliderMax(operationType, level),
            step = getSliderStep(operationType, level),
            medalDisabled = quizRange.min < QuizRange.Default(operationType, level).min,
            onRangeChanged = { min, max -> onRangeChanged(operationType, min, max) }
        )
    }
}

@Composable
private fun OperationRangeSlider(
    operationType: OperationType,
    currentMin: Int,
    currentMax: Int,
    sliderMax: Int,
    step: Int,
    medalDisabled: Boolean,
    onRangeChanged: (Int, Int) -> Unit
) {
    val tag = operationType.name.lowercase()
    val steps = maxOf(0, ((sliderMax - SLIDER_MIN_VALUE) / step) - 1)

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(
                Res.string.difficulty_range_label,
                stringResource(operationType.labelRes),
                currentMin,
                currentMax
            ),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.testTag("difficulty_label_$tag")
        )

        RangeSlider(
            value = currentMin.toFloat()..currentMax.toFloat(),
            onValueChange = { range ->
                val newMin = roundToStep(range.start, step)
                val newMax = roundToStep(range.endInclusive, step)
                if (newMin <= newMax) {
                    onRangeChanged(newMin, newMax)
                }
            },
            valueRange = SLIDER_MIN_VALUE.toFloat()..sliderMax.toFloat(),
            steps = steps,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("difficulty_slider_$tag")
        )

        if (medalDisabled) {
            Text(
                text = stringResource(Res.string.difficulty_medal_disabled),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.testTag("difficulty_warning_$tag")
            )
        }
    }
}

private fun roundToStep(value: Float, step: Int): Int {
    val offset = value - SLIDER_MIN_VALUE
    val rounded = (offset / step).toInt() * step + SLIDER_MIN_VALUE
    return rounded.coerceAtLeast(SLIDER_MIN_VALUE)
}

/**
 * 演算タイプとレベルに応じたスライダー上限を返す。
 */
private fun getSliderMax(operationType: OperationType, level: Level): Int =
    when (operationType) {
        OperationType.Addition -> when (level) {
            Level.Easy -> 20
            Level.Normal -> 100
            Level.Difficult -> 9999
        }

        OperationType.Subtraction -> when (level) {
            Level.Easy -> 20
            Level.Normal -> 200
            Level.Difficult -> 9999
        }

        OperationType.Multiplication, OperationType.Division -> when (level) {
            Level.Easy -> 20
            Level.Normal -> 50
            Level.Difficult -> 200
        }

        OperationType.All -> 1 // 全ての難易度調整には未対応
    }

/**
 * 演算タイプとレベルに応じたスライダーのステップを返す。
 */
private fun getSliderStep(operationType: OperationType, level: Level): Int =
    when (operationType) {
        OperationType.Addition -> when (level) {
            Level.Easy -> 1
            Level.Normal -> 1
            Level.Difficult -> 10
        }

        OperationType.Subtraction -> when (level) {
            Level.Easy -> 1
            Level.Normal -> 1
            Level.Difficult -> 10
        }

        OperationType.Multiplication, OperationType.Division -> when (level) {
            Level.Easy -> 1
            Level.Normal -> 1
            Level.Difficult -> 1
        }

        OperationType.All -> 1 // 全ての難易度調整には未対応
    }
