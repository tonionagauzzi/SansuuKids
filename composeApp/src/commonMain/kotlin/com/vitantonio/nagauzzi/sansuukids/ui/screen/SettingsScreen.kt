package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.ui.component.AppHeader
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.settings
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
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .safeContentPadding()
    ) {
        SettingsScreenPortrait(
            perQuestionAnswerCheckEnabled = perQuestionAnswerCheckEnabled,
            hintDisplayEnabled = hintDisplayEnabled,
            onPerQuestionAnswerCheckChanged = onPerQuestionAnswerCheckChanged,
            onHintDisplayChanged = onHintDisplayChanged,
            onBackClick = onBackClick
        )
    }
}

@Composable
private fun SettingsScreenPortrait(
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
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        AppHeader(
            title = stringResource(Res.string.settings),
            isMultiLine = false,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("settings_title"),
            onBackClick = onBackClick
        )

        Spacer(Modifier.size(16.dp))

        // Settings content
        SettingsContent(
            perQuestionAnswerCheckEnabled = perQuestionAnswerCheckEnabled,
            hintDisplayEnabled = hintDisplayEnabled,
            onPerQuestionAnswerCheckChanged = onPerQuestionAnswerCheckChanged,
            onHintDisplayChanged = onHintDisplayChanged
        )
    }
}

@Composable
private fun SettingsContent(
    perQuestionAnswerCheckEnabled: Boolean,
    hintDisplayEnabled: Boolean,
    onPerQuestionAnswerCheckChanged: (Boolean) -> Unit,
    onHintDisplayChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Per-question answer check setting
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.settings_per_question_check),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f)
            )

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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.settings_hint_display),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f)
            )

            Switch(
                checked = hintDisplayEnabled,
                onCheckedChange = { enabled ->
                    onHintDisplayChanged(enabled)
                },
                modifier = Modifier.testTag("hint_display_switch")
            )
        }
    }
}

@Preview(widthDp = 360, heightDp = 640) // 縦画面
@Preview(widthDp = 640, heightDp = 360) // 横画面
@Preview(widthDp = 480, heightDp = 480) // 正方形画面
@Preview(widthDp = 481, heightDp = 480) // 僅かに横画面
@Composable
private fun SettingsScreenPreview() {
    SansuuKidsTheme {
        SettingsScreen(
            perQuestionAnswerCheckEnabled = false,
            hintDisplayEnabled = false,
            onPerQuestionAnswerCheckChanged = {},
            onHintDisplayChanged = {},
            onBackClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}

@Preview(widthDp = 360, heightDp = 640) // 縦画面
@Preview(widthDp = 640, heightDp = 360) // 横画面
@Composable
private fun SettingsScreenPreviewEnabled() {
    SansuuKidsTheme {
        SettingsScreen(
            perQuestionAnswerCheckEnabled = true,
            hintDisplayEnabled = true,
            onPerQuestionAnswerCheckChanged = {},
            onHintDisplayChanged = {},
            onBackClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}
