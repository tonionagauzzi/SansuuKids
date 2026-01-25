package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
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
import sansuukids.composeapp.generated.resources.settings
import sansuukids.composeapp.generated.resources.settings_back
import sansuukids.composeapp.generated.resources.settings_hint_display
import sansuukids.composeapp.generated.resources.settings_per_question_check

@Composable
fun SettingsScreen(
    perQuestionAnswerCheckEnabled: Boolean,
    hintDisplayEnabled: Boolean,
    onPerQuestionAnswerCheckChanged: (Boolean) -> Unit,
    onHintDisplayChanged: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeContentPadding()
            .padding(horizontal = 32.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Title
        Text(
            text = stringResource(Res.string.settings),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.testTag("settings_title")
        )

        // Settings content
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f).padding(vertical = 48.dp)
        ) {
            // Per-question answer check setting
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.settings_per_question_check),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.padding(horizontal = 16.dp))

                Switch(
                    checked = perQuestionAnswerCheckEnabled,
                    onCheckedChange = { enabled ->
                        onPerQuestionAnswerCheckChanged(enabled)
                    },
                    modifier = Modifier.testTag("per_question_check_switch")
                )
            }

            // Hint display setting (Easy mode only)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.settings_hint_display),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.padding(horizontal = 16.dp))

                Switch(
                    checked = hintDisplayEnabled,
                    onCheckedChange = { enabled ->
                        onHintDisplayChanged(enabled)
                    },
                    modifier = Modifier.testTag("hint_display_switch")
                )
            }
        }

        // Back button
        LargeButton(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            text = stringResource(Res.string.settings_back),
            textStyle = MaterialTheme.typography.headlineMedium,
            onClick = onBackClick,
            modifier = Modifier.height(72.dp).testTag("settings_back_button")
        )
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    SansuuKidsTheme {
        SettingsScreen(
            perQuestionAnswerCheckEnabled = false,
            hintDisplayEnabled = false,
            onPerQuestionAnswerCheckChanged = {},
            onHintDisplayChanged = {},
            onBackClick = {}
        )
    }
}

@Preview
@Composable
private fun SettingsScreenPreviewEnabled() {
    SansuuKidsTheme {
        SettingsScreen(
            perQuestionAnswerCheckEnabled = true,
            hintDisplayEnabled = true,
            onPerQuestionAnswerCheckChanged = {},
            onHintDisplayChanged = {},
            onBackClick = {}
        )
    }
}
