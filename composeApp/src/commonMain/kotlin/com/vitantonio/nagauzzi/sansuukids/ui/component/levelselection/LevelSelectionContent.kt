package com.vitantonio.nagauzzi.sansuukids.ui.component.levelselection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.ui.component.LargeButton
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.level_difficult
import sansuukids.composeapp.generated.resources.level_easy
import sansuukids.composeapp.generated.resources.level_normal
import sansuukids.composeapp.generated.resources.setting
import sansuukids.composeapp.generated.resources.settings

@Composable
internal fun LevelSelectionContent(
    onEasyClick: () -> Unit,
    onNormalClick: () -> Unit,
    onDifficultClick: () -> Unit,
    onSettingClick: (Level) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        LevelButtonRow(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            text = stringResource(Res.string.level_easy),
            onClick = onEasyClick,
            onSettingClick = { onSettingClick(Level.Easy) },
            buttonTestTag = "easy_button",
            settingsTestTag = "easy_setting_button"
        )

        LevelButtonRow(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            text = stringResource(Res.string.level_normal),
            onClick = onNormalClick,
            onSettingClick = { onSettingClick(Level.Normal) },
            buttonTestTag = "normal_button",
            settingsTestTag = "normal_setting_button"
        )

        LevelButtonRow(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            text = stringResource(Res.string.level_difficult),
            onClick = onDifficultClick,
            onSettingClick = { onSettingClick(Level.Difficult) },
            buttonTestTag = "difficult_button",
            settingsTestTag = "difficult_setting_button"
        )
    }
}

@Composable
private fun LevelButtonRow(
    containerColor: Color,
    contentColor: Color,
    text: String,
    onClick: () -> Unit,
    onSettingClick: () -> Unit,
    buttonTestTag: String,
    settingsTestTag: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LargeButton(
            containerColor = containerColor,
            contentColor = contentColor,
            text = text,
            textAlign = TextAlign.Center,
            textStyle = MaterialTheme.typography.headlineSmall,
            onClick = onClick,
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .testTag(buttonTestTag)
        )

        IconButton(
            onClick = onSettingClick,
            modifier = Modifier.testTag(settingsTestTag)
        ) {
            Icon(
                painter = painterResource(Res.drawable.settings),
                contentDescription = stringResource(Res.string.setting),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}
