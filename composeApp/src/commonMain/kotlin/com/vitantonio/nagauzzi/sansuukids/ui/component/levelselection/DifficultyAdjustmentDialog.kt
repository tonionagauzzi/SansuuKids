package com.vitantonio.nagauzzi.sansuukids.ui.component.levelselection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vitantonio.nagauzzi.sansuukids.logic.GenerateQuiz
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import com.vitantonio.nagauzzi.sansuukids.model.labelRes
import org.jetbrains.compose.resources.stringResource
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.difficulty_adjustment_title
import sansuukids.composeapp.generated.resources.difficulty_close
import sansuukids.composeapp.generated.resources.difficulty_max_value
import sansuukids.composeapp.generated.resources.difficulty_medal_disabled
import sansuukids.composeapp.generated.resources.difficulty_min_value
import sansuukids.composeapp.generated.resources.difficulty_range_label
import sansuukids.composeapp.generated.resources.difficulty_reset

@Composable
internal fun DifficultyAdjustmentDialog(
    level: Level,
    operationTypes: List<OperationType>,
    customRanges: List<QuizRange>,
    onRangeChanged: (OperationType, Int, Int) -> Unit,
    onReset: (OperationType) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.testTag("difficulty_dialog"),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(Res.string.difficulty_adjustment_title),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.testTag("difficulty_dialog_title")
                )

                Text(
                    text = stringResource(level.labelRes),
                    style = MaterialTheme.typography.titleSmall
                )

                operationTypes.forEach { operationType ->
                    val defaultRange = QuizRange.Default(operationType, level)
                    val customRange = customRanges.find {
                        it.operationType == operationType && it.level == level
                    }
                    val currentMin = customRange?.min ?: defaultRange.min
                    val currentMax = customRange?.max ?: defaultRange.max
                    val sliderMax = getSliderMax(operationType, level)
                    val step = getSliderStep(operationType, level)
                    val medalDisabled = currentMin < defaultRange.min

                    OperationRangeSlider(
                        operationType = operationType,
                        currentMin = currentMin,
                        currentMax = currentMax,
                        sliderMin = 1,
                        sliderMax = sliderMax,
                        step = step,
                        medalDisabled = medalDisabled,
                        onRangeChanged = { min, max -> onRangeChanged(operationType, min, max) },
                        onReset = { onReset(operationType) }
                    )
                }

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.testTag("difficulty_close_button")
                ) {
                    Text(text = stringResource(Res.string.difficulty_close))
                }
            }
        }
    }
}

@Composable
private fun OperationRangeSlider(
    operationType: OperationType,
    currentMin: Int,
    currentMax: Int,
    sliderMin: Int,
    sliderMax: Int,
    step: Int,
    medalDisabled: Boolean,
    onRangeChanged: (Int, Int) -> Unit,
    onReset: () -> Unit
) {
    val tag = operationType.name.lowercase()
    val steps = maxOf(0, ((sliderMax - sliderMin) / step) - 1)

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(
                Res.string.difficulty_range_label,
                stringResource(operationType.labelRes)
            ),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.testTag("difficulty_label_$tag")
        )

        RangeSlider(
            value = currentMin.toFloat()..currentMax.toFloat(),
            onValueChange = { range ->
                val newMin = roundToStep(range.start, step, sliderMin)
                val newMax = roundToStep(range.endInclusive, step, sliderMin)
                if (newMin <= newMax) {
                    onRangeChanged(newMin, newMax)
                }
            },
            valueRange = sliderMin.toFloat()..sliderMax.toFloat(),
            steps = steps,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("difficulty_slider_$tag")
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(Res.string.difficulty_min_value, currentMin),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("difficulty_min_$tag")
            )
            Text(
                text = stringResource(Res.string.difficulty_max_value, currentMax),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("difficulty_max_$tag")
            )
        }

        if (medalDisabled) {
            Text(
                text = stringResource(Res.string.difficulty_medal_disabled),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.testTag("difficulty_warning_$tag")
            )
        }

        Button(
            onClick = onReset,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            modifier = Modifier.testTag("difficulty_reset_$tag")
        ) {
            Text(text = stringResource(Res.string.difficulty_reset))
        }
    }
}

private fun roundToStep(value: Float, step: Int, min: Int): Int {
    val offset = value - min
    val rounded = (offset / step).toInt() * step + min
    return rounded.coerceAtLeast(min)
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
    }
