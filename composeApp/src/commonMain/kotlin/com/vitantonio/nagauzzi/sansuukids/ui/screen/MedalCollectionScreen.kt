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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.model.MedalDisplay
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.model.emojiRes
import com.vitantonio.nagauzzi.sansuukids.model.labelRes
import com.vitantonio.nagauzzi.sansuukids.ui.component.AppHeader
import com.vitantonio.nagauzzi.sansuukids.ui.component.GridCell
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
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        AppHeader(
            title = stringResource(Res.string.medal_collection_title),
            isMultiLine = false,
            onBackClick = onBackClick,
            modifier = Modifier.testTag("medal_collection_title")
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            MedalGrid(
                medalDisplays = medalDisplays,
                modifier = Modifier.testTag("medal_grid")
            )
        }
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
                    val medal = medalDisplays.firstOrNull {
                        it.mode == mode && it.level == level
                    }?.medal ?: Medal.Nothing
                    GridCell(
                        text = stringResource(medal.emojiRes),
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

@Preview(widthDp = 360, heightDp = 640) // 縦画面
@Preview(widthDp = 640, heightDp = 360) // 横画面
@Preview(widthDp = 480, heightDp = 480) // 正方形画面
@Preview(widthDp = 481, heightDp = 480) // 僅かに横画面
@Composable
private fun MedalCollectionScreenPreview() {
    SansuuKidsTheme {
        MedalCollectionScreen(
            medalDisplays = listOf(
                MedalDisplay(Mode.ADDITION, Level.EASY, Medal.Gold),
                MedalDisplay(Mode.ADDITION, Level.NORMAL, Medal.Silver),
                MedalDisplay(Mode.ADDITION, Level.DIFFICULT, Medal.Bronze),
                MedalDisplay(Mode.SUBTRACTION, Level.EASY, Medal.Silver),
                MedalDisplay(Mode.SUBTRACTION, Level.NORMAL, Medal.Bronze),
                MedalDisplay(Mode.MULTIPLICATION, Level.EASY, Medal.Bronze),
                MedalDisplay(Mode.ALL, Level.DIFFICULT, Medal.Gold)
            ),
            onBackClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}
