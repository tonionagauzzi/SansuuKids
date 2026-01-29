package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.ui.component.LargeButton
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.app_icon
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
    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        if (maxWidth > maxHeight) {
            HomeScreenLandscape(
                onStartClick = onStartClick,
                onMedalCollectionClick = onMedalCollectionClick,
                onSettingsClick = onSettingsClick
            )
        } else {
            HomeScreenPortrait(
                onStartClick = onStartClick,
                onMedalCollectionClick = onMedalCollectionClick,
                onSettingsClick = onSettingsClick
            )
        }
    }
}

@Composable
private fun HomeScreenPortrait(
    onStartClick: () -> Unit,
    onMedalCollectionClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.app_icon),
            contentDescription = stringResource(Res.string.app_title),
            modifier = Modifier
                .weight(1f)
                .sizeIn(maxWidth = 400.dp, maxHeight = 400.dp)
                .aspectRatio(1f)
                .testTag("home_title"),
            contentScale = ContentScale.Fit
        )

        HomeButtons(
            onStartClick = onStartClick,
            onMedalCollectionClick = onMedalCollectionClick,
            onSettingsClick = onSettingsClick,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun HomeScreenLandscape(
    onStartClick: () -> Unit,
    onMedalCollectionClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(Res.drawable.app_icon),
            contentDescription = stringResource(Res.string.app_title),
            modifier = Modifier
                .weight(1f)
                .sizeIn(maxWidth = 300.dp, maxHeight = 300.dp)
                .aspectRatio(1f)
                .testTag("home_title"),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.width(32.dp))

        HomeButtons(
            onStartClick = onStartClick,
            onMedalCollectionClick = onMedalCollectionClick,
            onSettingsClick = onSettingsClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun HomeButtons(
    onStartClick: () -> Unit,
    onMedalCollectionClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    spacing: Dp = 24.dp
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        LargeButton(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            text = stringResource(Res.string.start),
            textStyle = MaterialTheme.typography.headlineMedium,
            onClick = onStartClick,
            modifier = Modifier.height(72.dp).testTag("start_button")
        )

        LargeButton(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            text = stringResource(Res.string.medal_collection),
            textStyle = MaterialTheme.typography.headlineMedium,
            onClick = onMedalCollectionClick,
            modifier = Modifier.height(72.dp).testTag("medal_collection_button")
        )

        LargeButton(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            text = stringResource(Res.string.settings),
            textStyle = MaterialTheme.typography.headlineMedium,
            onClick = onSettingsClick,
            modifier = Modifier.height(72.dp).testTag("settings_button")
        )
    }
}

@Preview(widthDp = 360, heightDp = 640) // 縦画面
@Preview(widthDp = 640, heightDp = 360) // 横画面
@Preview(widthDp = 480, heightDp = 480) // 正方形画面
@Preview(widthDp = 481, heightDp = 480) // 僅かに横画面
@Composable
private fun HomeScreenPreview() {
    SansuuKidsTheme {
        HomeScreen(
            onStartClick = {},
            onMedalCollectionClick = {},
            onSettingsClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}
