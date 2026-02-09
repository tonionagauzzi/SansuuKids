package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.model.Level.Difficult
import com.vitantonio.nagauzzi.sansuukids.model.Level.Easy
import com.vitantonio.nagauzzi.sansuukids.model.Level.Normal
import com.vitantonio.nagauzzi.sansuukids.model.Medal.Nothing
import com.vitantonio.nagauzzi.sansuukids.model.MedalCounter
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import com.vitantonio.nagauzzi.sansuukids.model.bestMedal
import com.vitantonio.nagauzzi.sansuukids.model.findOrDefault
import com.vitantonio.nagauzzi.sansuukids.model.emojiRes
import com.vitantonio.nagauzzi.sansuukids.model.labelRes
import com.vitantonio.nagauzzi.sansuukids.ui.component.AppHeader
import com.vitantonio.nagauzzi.sansuukids.ui.component.GridCell
import com.vitantonio.nagauzzi.sansuukids.ui.component.medal.MedalDetailDialog
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.medal_collection_title

@Composable
internal fun MedalCollectionScreen(
    medalCounters: List<MedalCounter>,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    var selectedCounter by remember { mutableStateOf<MedalCounter?>(null) }

    BoxWithConstraints(
        modifier = modifier
            .safeContentPadding()
            .fillMaxSize()
    ) {
        val isLandscape = maxWidth > maxHeight

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AppHeader(
                title = stringResource(Res.string.medal_collection_title),
                isMultiLine = !isLandscape,
                onBackClick = onBackClick,
                modifier = Modifier.testTag("medal_collection_title")
            )

            MedalGrid(
                medalCounters = medalCounters,
                onMedalClick = { counter -> selectedCounter = counter },
                modifier = Modifier
                    .padding(16.dp)
                    .testTag("medal_grid")
                    .verticalScroll(rememberScrollState())
            )
        }
    }

    selectedCounter?.let { counter ->
        MedalDetailDialog(
            medalCounter = counter,
            onDismiss = { selectedCounter = null }
        )
    }
}

@Composable
private fun MedalGrid(
    medalCounters: List<MedalCounter>,
    onMedalClick: (MedalCounter) -> Unit,
    modifier: Modifier = Modifier
) {
    val operationTypes = listOf(
        OperationType.Addition,
        OperationType.Subtraction,
        OperationType.Multiplication,
        OperationType.Division,
        OperationType.All
    )
    val levels = listOf(Easy, Normal, Difficult)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = MaterialTheme.colorScheme.outline)
    ) {
        // Header row
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Empty corner cell
            GridCell(
                text = "",
                isHeader = true,
                modifier = Modifier
                    .weight(1.2f)
                    .testTag("grid_corner")
            )
            // Level headers
            levels.forEach { level ->
                GridCell(
                    text = stringResource(level.labelRes),
                    isHeader = true,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("grid_header_${level.name}")
                )
            }
        }

        // Mode rows
        operationTypes.forEach { mode ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Mode label
                GridCell(
                    text = stringResource(mode.labelRes),
                    isHeader = true,
                    modifier = Modifier
                        .weight(1.2f)
                        .testTag("grid_header_${mode.name.lowercase()}")
                )
                // Medal cells
                levels.forEach { level ->
                    val medalCounter = medalCounters.findOrDefault(mode, level)
                    GridCell(
                        text = stringResource(medalCounter.bestMedal.emojiRes),
                        isHeader = false,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("grid_cell_${mode.name.lowercase()}_${level.name.lowercase()}")
                            .clickable {
                                if (medalCounter.bestMedal != Nothing) {
                                    onMedalClick(medalCounter)
                                }
                            }
                    )
                }
            }
        }
    }
}

@Preview(widthDp = 360, heightDp = 640) // 縦画面
@Preview(widthDp = 640, heightDp = 360) // 横画面
@Preview(widthDp = 480, heightDp = 480) // 正方形画面
@Preview(widthDp = 481, heightDp = 480) // 僅かに横画面
@Composable
private fun MedalCollectionScreenPreview() {
    SansuuKidsTheme {
        MedalCollectionScreen(
            medalCounters = listOf(
                MedalCounter(
                    operationType = OperationType.Addition,
                    level = Easy,
                    gold = 15,
                    silver = 11,
                    bronze = 3,
                    star = 0
                ),
                MedalCounter(
                    operationType = OperationType.Addition,
                    level = Normal,
                    gold = 0,
                    silver = 1,
                    bronze = 2,
                    star = 4
                ),
                MedalCounter(
                    operationType = OperationType.Addition,
                    level = Difficult,
                    gold = 0,
                    silver = 0,
                    bronze = 0,
                    star = 1
                ),
                MedalCounter(
                    operationType = OperationType.Subtraction,
                    level = Easy,
                    gold = 5,
                    silver = 3,
                    bronze = 0,
                    star = 0
                )
            ),
            onBackClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}
