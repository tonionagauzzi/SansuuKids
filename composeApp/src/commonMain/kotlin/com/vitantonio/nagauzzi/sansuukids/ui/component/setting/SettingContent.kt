package com.vitantonio.nagauzzi.sansuukids.ui.component.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.settings_hint_display
import sansuukids.composeapp.generated.resources.settings_per_question_check

@Composable
internal fun SettingsContent(
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
            horizontalArrangement = Arrangement.SpaceBetween,
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
