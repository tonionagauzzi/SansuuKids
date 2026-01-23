package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
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
import sansuukids.composeapp.generated.resources.start

@Composable
fun HomeScreen(
    onStartClick: () -> Unit,
    onMedalCollectionClick: () -> Unit,
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
        Image(
            painter = painterResource(Res.drawable.app_icon),
            contentDescription = stringResource(Res.string.app_title),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .testTag("home_title"),
            contentScale = ContentScale.Fit
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
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
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    SansuuKidsTheme {
        HomeScreen(
            onStartClick = {},
            onMedalCollectionClick = {}
        )
    }
}
