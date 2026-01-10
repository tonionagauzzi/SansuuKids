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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.ui.component.LargeButton
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

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
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = "さんすう\nキッズ",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        LargeButton(
            text = "スタート",
            onClick = onStartClick,
            containerColor = MaterialTheme.colorScheme.primary
        )

        LargeButton(
            text = "メダルずかん",
            onClick = onMedalCollectionClick,
            containerColor = MaterialTheme.colorScheme.secondary
        )

        LargeButton(
            text = "せってい",
            onClick = onSettingsClick,
            containerColor = MaterialTheme.colorScheme.tertiary
        )
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
