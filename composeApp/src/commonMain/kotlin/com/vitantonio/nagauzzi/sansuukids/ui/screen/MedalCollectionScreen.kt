package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.model.MedalDisplay
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.model.emojiRes
import com.vitantonio.nagauzzi.sansuukids.model.labelRes
import com.vitantonio.nagauzzi.sansuukids.ui.component.GridCell
import com.vitantonio.nagauzzi.sansuukids.ui.component.LargeButton
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.medal_collection_back
import sansuukids.composeapp.generated.resources.medal_collection_not_earned
import sansuukids.composeapp.generated.resources.medal_collection_title

@Composable
internal fun MedalCollectionScreen(
    medalDisplays: List<MedalDisplay>,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeContentPadding()
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(Res.string.medal_collection_title),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.testTag("medal_collection_title")
        )

        Spacer(modifier = Modifier.height(24.dp))

        MedalGrid(
            medalDisplays = medalDisplays,
            modifier = Modifier.testTag("medal_grid")
        )

        Spacer(modifier = Modifier.height(32.dp))

        LargeButton(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            text = stringResource(Res.string.medal_collection_back),
            textStyle = MaterialTheme.typography.headlineSmall,
            onClick = onBackClick,
            modifier = Modifier.height(56.dp).testTag("medal_collection_back_button")
        )
    }
}

@Composable
private fun MedalGrid(
    medalDisplays: List<MedalDisplay>,
    modifier: Modifier = Modifier
) {
    val modes =
        listOf(Mode.ADDITION, Mode.SUBTRACTION, Mode.MULTIPLICATION, Mode.DIVISION, Mode.ALL)
    val levels = listOf(Level.EASY, Level.NORMAL, Level.DIFFICULT)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline)
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
        modes.forEach { mode ->
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
                    val medal = medalDisplays.find { it.mode == mode && it.level == level }?.medal
                    val text = if (medal == null) {
                        stringResource(Res.string.medal_collection_not_earned)
                    } else {
                        stringResource(medal.emojiRes)
                    }
                    GridCell(
                        text = text,
                        isHeader = false,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("grid_cell_${mode.name.lowercase()}_${level.name.lowercase()}")
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MedalCollectionScreenPreview() {
    SansuuKidsTheme {
        MedalCollectionScreen(
            medalDisplays = emptyList(),
            onBackClick = {}
        )
    }
}

@Preview
@Composable
private fun MedalCollectionScreenWithMedalsPreview() {
    SansuuKidsTheme {
        MedalCollectionScreen(
            medalDisplays = listOf(
                MedalDisplay(
                    mode = Mode.ADDITION,
                    level = Level.EASY,
                    medal = Medal.Gold
                ),
                MedalDisplay(
                    mode = Mode.ADDITION,
                    level = Level.NORMAL,
                    medal = Medal.Silver
                ),
                MedalDisplay(
                    mode = Mode.SUBTRACTION,
                    level = Level.EASY,
                    medal = Medal.Bronze
                ),
                MedalDisplay(
                    mode = Mode.ALL,
                    level = Level.DIFFICULT,
                    medal = Medal.Star
                )
            ),
            onBackClick = {}
        )
    }
}
