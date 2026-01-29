package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.ui.component.AppHeader
import com.vitantonio.nagauzzi.sansuukids.ui.component.levelselection.LevelSelector
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.level_selection_title

@Composable
fun LevelSelectionScreen(
    onEasyClick: () -> Unit,
    onNormalClick: () -> Unit,
    onDifficultClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        val isLandscape = maxWidth > maxHeight

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AppHeader(
                title = stringResource(Res.string.level_selection_title),
                isMultiLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("level_selection_title"),
                onBackClick = onBackClick
            )

            Box (
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                LevelSelector(
                    onEasyClick = onEasyClick,
                    onNormalClick = onNormalClick,
                    onDifficultClick = onDifficultClick,
                    modifier = if (isLandscape) {
                        Modifier.fillMaxWidth(0.3f)
                    } else {
                        Modifier.fillMaxWidth()
                    }
                )
            }
        }
    }
}

@Preview(widthDp = 360, heightDp = 640) // 縦画面
@Preview(widthDp = 640, heightDp = 360) // 横画面
@Preview(widthDp = 480, heightDp = 480) // 正方形画面
@Preview(widthDp = 481, heightDp = 480) // 僅かに横画面
@Composable
private fun LevelSelectionScreenPreview() {
    SansuuKidsTheme {
        LevelSelectionScreen(
            onEasyClick = {},
            onNormalClick = {},
            onDifficultClick = {},
            onBackClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}
