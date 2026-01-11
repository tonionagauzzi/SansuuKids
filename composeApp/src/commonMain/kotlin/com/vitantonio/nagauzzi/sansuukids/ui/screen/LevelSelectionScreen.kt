package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.ui.component.LargeButton
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.level_difficult
import sansuukids.composeapp.generated.resources.level_easy
import sansuukids.composeapp.generated.resources.level_normal
import sansuukids.composeapp.generated.resources.level_selection_title

@Composable
fun LevelSelectionScreen(
    onEasyClick: () -> Unit,
    onNormalClick: () -> Unit,
    onDifficultClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeContentPadding()
            .padding(horizontal = 32.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(Res.string.level_selection_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.testTag("level_selection_title")
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LargeButton(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                text = stringResource(Res.string.level_easy),
                textStyle = MaterialTheme.typography.headlineSmall,
                onClick = onEasyClick,
                modifier = Modifier.height(56.dp).testTag("easy_button")
            )

            LargeButton(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                text = stringResource(Res.string.level_normal),
                textStyle = MaterialTheme.typography.headlineSmall,
                onClick = onNormalClick,
                modifier = Modifier.height(56.dp).testTag("normal_button")
            )

            LargeButton(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                text = stringResource(Res.string.level_difficult),
                textStyle = MaterialTheme.typography.headlineSmall,
                onClick = onDifficultClick,
                modifier = Modifier.height(56.dp).testTag("difficult_button")
            )
        }
    }
}

@Preview
@Composable
private fun LevelSelectionScreenPreview() {
    SansuuKidsTheme {
        LevelSelectionScreen(
            onEasyClick = {},
            onNormalClick = {},
            onDifficultClick = {}
        )
    }
}
