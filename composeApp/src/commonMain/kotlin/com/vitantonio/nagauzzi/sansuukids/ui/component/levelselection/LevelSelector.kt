package com.vitantonio.nagauzzi.sansuukids.ui.component.levelselection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.ui.component.LargeButton
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.level_difficult
import sansuukids.composeapp.generated.resources.level_easy
import sansuukids.composeapp.generated.resources.level_normal

@Composable
internal fun LevelSelector(
    onEasyClick: () -> Unit,
    onNormalClick: () -> Unit,
    onDifficultClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        LargeButton(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            text = stringResource(Res.string.level_easy),
            textAlign = TextAlign.Center,
            textStyle = MaterialTheme.typography.headlineSmall,
            onClick = onEasyClick,
            modifier = Modifier
                .height(56.dp)
                .testTag("easy_button")
        )

        LargeButton(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            text = stringResource(Res.string.level_normal),
            textAlign = TextAlign.Center,
            textStyle = MaterialTheme.typography.headlineSmall,
            onClick = onNormalClick,
            modifier = Modifier
                .height(56.dp)
                .testTag("normal_button")
        )

        LargeButton(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            text = stringResource(Res.string.level_difficult),
            textAlign = TextAlign.Center,
            textStyle = MaterialTheme.typography.headlineSmall,
            onClick = onDifficultClick,
            modifier = Modifier
                .height(56.dp)
                .testTag("difficult_button")
        )
    }
}

@Preview
@Composable
private fun LevelSelectorPreview() {
    SansuuKidsTheme {
        LevelSelector(
            onEasyClick = {},
            onNormalClick = {},
            onDifficultClick = {}
        )
    }
}
