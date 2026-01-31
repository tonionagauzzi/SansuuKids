package com.vitantonio.nagauzzi.sansuukids.ui.component.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.model.descriptionRes
import com.vitantonio.nagauzzi.sansuukids.model.emojiRes
import com.vitantonio.nagauzzi.sansuukids.ui.component.LargeButton
import org.jetbrains.compose.resources.stringResource
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.result_check_answers
import sansuukids.composeapp.generated.resources.result_home
import sansuukids.composeapp.generated.resources.result_retry
import sansuukids.composeapp.generated.resources.result_score
import sansuukids.composeapp.generated.resources.result_title

@Composable
internal fun ResultContent(
    isLandscape: Boolean,
    score: Int,
    medal: Medal,
    onCheckAnswersClick: () -> Unit,
    onRetryClick: () -> Unit,
    onHomeClick: () -> Unit,
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
            ResultInfo(score = score, medal = medal, modifier = Modifier.weight(1f))

            ResultButtons(
                onCheckAnswersClick = onCheckAnswersClick,
                onRetryClick = onRetryClick,
                onHomeClick = onHomeClick,
                modifier = Modifier.weight(1f)
            )
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            ResultInfo(score = score, medal = medal, modifier = Modifier.weight(1f))

            ResultButtons(
                onCheckAnswersClick = onCheckAnswersClick,
                onRetryClick = onRetryClick,
                onHomeClick = onHomeClick
            )
        }
    }
}

@Composable
private fun ResultInfo(
    score: Int,
    medal: Medal,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = stringResource(Res.string.result_title),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.testTag("result_title")
        )

        Text(
            text = stringResource(Res.string.result_score, score),
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.testTag("result_score")
        )

        val medalDescription = stringResource(medal.descriptionRes)

        Text(
            text = stringResource(medal.emojiRes),
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .testTag("result_medal")
                .semantics { contentDescription = medalDescription }
        )
    }
}

@Composable
private fun ResultButtons(
    onCheckAnswersClick: () -> Unit,
    onRetryClick: () -> Unit,
    onHomeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        LargeButton(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            text = stringResource(Res.string.result_check_answers),
            textStyle = MaterialTheme.typography.headlineSmall,
            onClick = onCheckAnswersClick,
            modifier = Modifier.height(56.dp).testTag("check_answers_button")
        )

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
