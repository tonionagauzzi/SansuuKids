package com.vitantonio.nagauzzi.sansuukids.ui.component.modeselection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.ui.component.LargeButton
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.mode_addition
import sansuukids.composeapp.generated.resources.mode_all
import sansuukids.composeapp.generated.resources.mode_division
import sansuukids.composeapp.generated.resources.mode_multiplication
import sansuukids.composeapp.generated.resources.mode_subtraction

private data class ModeButtonConfig(
    val containerColor: @Composable () -> Color,
    val contentColor: @Composable () -> Color,
    val textRes: StringResource,
    val onClick: () -> Unit,
    val testTag: String
)

@Composable
internal fun ModeSelector(
    isLandscape: Boolean,
    onAdditionClick: () -> Unit,
    onSubtractionClick: () -> Unit,
    onMultiplicationClick: () -> Unit,
    onDivisionClick: () -> Unit,
    onAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttons = listOf(
        ModeButtonConfig(
            containerColor = { MaterialTheme.colorScheme.primaryContainer },
            contentColor = { MaterialTheme.colorScheme.onPrimaryContainer },
            textRes = Res.string.mode_addition,
            onClick = onAdditionClick,
            testTag = "addition_button"
        ),
        ModeButtonConfig(
            containerColor = { MaterialTheme.colorScheme.secondaryContainer },
            contentColor = { MaterialTheme.colorScheme.onSecondaryContainer },
            textRes = Res.string.mode_subtraction,
            onClick = onSubtractionClick,
            testTag = "subtraction_button"
        ),
        ModeButtonConfig(
            containerColor = { MaterialTheme.colorScheme.tertiaryContainer },
            contentColor = { MaterialTheme.colorScheme.onTertiaryContainer },
            textRes = Res.string.mode_multiplication,
            onClick = onMultiplicationClick,
            testTag = "multiplication_button"
        ),
        ModeButtonConfig(
            containerColor = { MaterialTheme.colorScheme.primaryContainer },
            contentColor = { MaterialTheme.colorScheme.onPrimaryContainer },
            textRes = Res.string.mode_division,
            onClick = onDivisionClick,
            testTag = "division_button"
        ),
        ModeButtonConfig(
            containerColor = { MaterialTheme.colorScheme.secondaryContainer },
            contentColor = { MaterialTheme.colorScheme.onSecondaryContainer },
            textRes = Res.string.mode_all,
            onClick = onAllClick,
            testTag = "all_button"
        )
    )

    if (isLandscape) {
        FlowRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            maxItemsInEachRow = 3
        ) {
            buttons.forEach { config ->
                LargeButton(
                    containerColor = config.containerColor(),
                    contentColor = config.contentColor(),
                    text = stringResource(config.textRes),
                    textAlign = TextAlign.Center,
                    textStyle = MaterialTheme.typography.headlineSmall,
                    onClick = config.onClick,
                    modifier = Modifier
                        .height(56.dp)
                        .weight(1f)
                        .testTag(config.testTag)
                )
            }
        }
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier
        ) {
            buttons.forEach { config ->
                LargeButton(
                    containerColor = config.containerColor(),
                    contentColor = config.contentColor(),
                    text = stringResource(config.textRes),
                    textAlign = TextAlign.Center,
                    textStyle = MaterialTheme.typography.headlineSmall,
                    onClick = config.onClick,
                    modifier = Modifier
                        .height(56.dp)
                        .testTag(config.testTag)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ModeSelectorLandscapePreview() {
    SansuuKidsTheme {
        ModeSelector(
            isLandscape = true,
            onAdditionClick = {},
            onSubtractionClick = {},
            onMultiplicationClick = {},
            onDivisionClick = {},
            onAllClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}

@Preview
@Composable
private fun ModeSelectorPortraitPreview() {
    SansuuKidsTheme {
        ModeSelector(
            isLandscape = false,
            onAdditionClick = {},
            onSubtractionClick = {},
            onMultiplicationClick = {},
            onDivisionClick = {},
            onAllClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}
