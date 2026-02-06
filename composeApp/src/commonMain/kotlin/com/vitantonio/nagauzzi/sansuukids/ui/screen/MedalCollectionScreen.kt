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
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.model.MedalCount
import com.vitantonio.nagauzzi.sansuukids.model.MedalDisplay
import com.vitantonio.nagauzzi.sansuukids.model.Mode
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
    medalDisplays: List<MedalDisplay>,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    var selectedDisplay by remember { mutableStateOf<MedalDisplay?>(null) }

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
                medalDisplays = medalDisplays,
                onMedalClick = { display -> selectedDisplay = display },
                modifier = Modifier
                    .padding(16.dp)
                    .testTag("medal_grid")
                    .verticalScroll(rememberScrollState())
            )
        }
    }

    selectedDisplay?.let { display ->
        MedalDetailDialog(
            medalDisplay = display,
            onDismiss = { selectedDisplay = null }
        )
    }
}

@Composable
private fun MedalGrid(
    medalDisplays: List<MedalDisplay>,
    onMedalClick: (MedalDisplay) -> Unit,
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
                    val display = medalDisplays.firstOrNull {
                        it.mode == mode && it.level == level
                    }
                    val medal = display?.medal ?: Medal.Nothing
                    val cellModifier = Modifier
                        .weight(1f)
                        .testTag("grid_cell_${mode.name.lowercase()}_${level.name.lowercase()}")
                    GridCell(
                        text = stringResource(medal.emojiRes),
                        isHeader = false,
                        modifier = if (medal != Medal.Nothing && display != null) {
                            cellModifier.clickable { onMedalClick(display) }
                        } else {
                            cellModifier
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
            medalDisplays = listOf(
                MedalDisplay(Mode.ADDITION, Level.EASY, Medal.Gold, MedalCount(gold = 3, silver = 1)),
                MedalDisplay(Mode.ADDITION, Level.NORMAL, Medal.Silver, MedalCount(silver = 2, bronze = 1)),
                MedalDisplay(Mode.ADDITION, Level.DIFFICULT, Medal.Bronze, MedalCount(bronze = 1)),
                MedalDisplay(Mode.SUBTRACTION, Level.EASY, Medal.Silver, MedalCount(silver = 1, star = 2)),
                MedalDisplay(Mode.SUBTRACTION, Level.NORMAL, Medal.Bronze, MedalCount(bronze = 1)),
                MedalDisplay(Mode.MULTIPLICATION, Level.EASY, Medal.Bronze, MedalCount(bronze = 2)),
                MedalDisplay(Mode.ALL, Level.DIFFICULT, Medal.Gold, MedalCount(gold = 1))
            ),
            onBackClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}
