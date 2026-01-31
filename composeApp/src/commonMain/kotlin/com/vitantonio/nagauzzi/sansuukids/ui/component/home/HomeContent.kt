package com.vitantonio.nagauzzi.sansuukids.ui.component.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.app_icon
import sansuukids.composeapp.generated.resources.app_title
import sansuukids.composeapp.generated.resources.medal_collection
import sansuukids.composeapp.generated.resources.setting
import sansuukids.composeapp.generated.resources.start

@Composable
internal fun HomeContent(
    isLandscape: Boolean,
    onStartClick: () -> Unit,
    onMedalCollectionClick: () -> Unit,
    onSettingClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLandscape) {
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
                onSettingClick = onSettingClick,
                modifier = Modifier.weight(1f)
            )
        }
    } else {
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
                onSettingClick = onSettingClick,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun HomeButtons(
    onStartClick: () -> Unit,
    onMedalCollectionClick: () -> Unit,
    onSettingClick: () -> Unit,
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
            text = stringResource(Res.string.setting),
            textStyle = MaterialTheme.typography.headlineMedium,
            onClick = onSettingClick,
            modifier = Modifier.height(72.dp).testTag("settings_button")
        )
    }
}
