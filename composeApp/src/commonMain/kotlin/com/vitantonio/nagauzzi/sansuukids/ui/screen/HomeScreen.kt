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
import sansuukids.composeapp.generated.resources.app_title
import sansuukids.composeapp.generated.resources.medal_collection
import sansuukids.composeapp.generated.resources.settings
import sansuukids.composeapp.generated.resources.start

@Composable
fun HomeScreen(
    onStartClick: () -> Unit,
    onMedalCollectionClick: () -> Unit,
    onSettingsClick: () -> Unit,
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
            text = stringResource(Res.string.app_title),
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.testTag("home_title")
        )

        Spacer(modifier = Modifier.height(56.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            LargeButton(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                text = stringResource(Res.string.start),
                textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                textStyle = MaterialTheme.typography.headlineMedium,
                onClick = onStartClick,
                modifier = Modifier.height(72.dp).testTag("start_button")
            )

            LargeButton(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                text = stringResource(Res.string.medal_collection),
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                textStyle = MaterialTheme.typography.headlineMedium,
                onClick = onMedalCollectionClick,
                modifier = Modifier.height(72.dp).testTag("medal_collection_button")
            )

            LargeButton(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                text = stringResource(Res.string.settings),
                textColor = MaterialTheme.colorScheme.onTertiaryContainer,
                textStyle = MaterialTheme.typography.headlineMedium,
                onClick = onSettingsClick,
                modifier = Modifier.height(72.dp).testTag("settings_button")
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    SansuuKidsTheme {
        HomeScreen(
            onStartClick = {},
            onMedalCollectionClick = {},
            onSettingsClick = {}
        )
    }
}
