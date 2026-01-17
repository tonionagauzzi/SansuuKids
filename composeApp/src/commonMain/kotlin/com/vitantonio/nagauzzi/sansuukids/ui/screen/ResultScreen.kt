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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.model.descriptionRes
import com.vitantonio.nagauzzi.sansuukids.model.emojiRes
import com.vitantonio.nagauzzi.sansuukids.ui.component.LargeButton
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.result_home
import sansuukids.composeapp.generated.resources.result_retry
import sansuukids.composeapp.generated.resources.result_score
import sansuukids.composeapp.generated.resources.result_title

@Composable
internal fun ResultScreen(
    score: Int,
    medal: Medal,
    onRetryClick: () -> Unit,
    onHomeClick: () -> Unit,
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
            text = stringResource(Res.string.result_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.testTag("result_title")
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(Res.string.result_score, score),
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.testTag("result_score")
        )

        Spacer(modifier = Modifier.height(24.dp))

        val medalDescription = stringResource(medal.descriptionRes)

        Text(
            text = stringResource(medal.emojiRes),
            fontSize = 96.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .testTag("result_medal")
                .semantics { contentDescription = medalDescription }
        )

        Spacer(modifier = Modifier.height(48.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LargeButton(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                text = stringResource(Res.string.result_retry),
                textStyle = MaterialTheme.typography.headlineSmall,
                onClick = onRetryClick,
                modifier = Modifier.height(56.dp).testTag("retry_button")
            )

            LargeButton(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                text = stringResource(Res.string.result_home),
                textStyle = MaterialTheme.typography.headlineSmall,
                onClick = onHomeClick,
                modifier = Modifier.height(56.dp).testTag("home_button")
            )
        }
    }
}

@Preview
@Composable
private fun ResultScreenPreview() {
    SansuuKidsTheme {
        ResultScreen(
            score = 80,
            medal = Medal.Silver,
            onRetryClick = {},
            onHomeClick = {}
        )
    }
}
