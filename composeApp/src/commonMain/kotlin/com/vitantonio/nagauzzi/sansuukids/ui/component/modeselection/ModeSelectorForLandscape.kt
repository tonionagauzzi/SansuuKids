package com.vitantonio.nagauzzi.sansuukids.ui.component.modeselection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.ui.component.LargeButton
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.mode_addition
import sansuukids.composeapp.generated.resources.mode_all
import sansuukids.composeapp.generated.resources.mode_division
import sansuukids.composeapp.generated.resources.mode_multiplication
import sansuukids.composeapp.generated.resources.mode_subtraction

@Composable
internal fun ModeSelectorForLandscape(
    onAdditionClick: () -> Unit,
    onSubtractionClick: () -> Unit,
    onMultiplicationClick: () -> Unit,
    onDivisionClick: () -> Unit,
    onAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        maxItemsInEachRow = 3
    ) {
        LargeButton(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            text = stringResource(Res.string.mode_addition),
            textAlign = TextAlign.Center,
            textStyle = MaterialTheme.typography.headlineSmall,
            onClick = onAdditionClick,
            modifier = Modifier
                .height(56.dp)
                .weight(1f)
                .testTag("addition_button")
        )

        LargeButton(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            text = stringResource(Res.string.mode_subtraction),
            textAlign = TextAlign.Center,
            textStyle = MaterialTheme.typography.headlineSmall,
            onClick = onSubtractionClick,
            modifier = Modifier
                .height(56.dp)
                .weight(1f)
                .testTag("subtraction_button")
        )

        LargeButton(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            text = stringResource(Res.string.mode_multiplication),
            textAlign = TextAlign.Center,
            textStyle = MaterialTheme.typography.headlineSmall,
            onClick = onMultiplicationClick,
            modifier = Modifier
                .height(56.dp)
                .weight(1f)
                .testTag("multiplication_button")
        )

        LargeButton(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            text = stringResource(Res.string.mode_division),
            textAlign = TextAlign.Center,
            textStyle = MaterialTheme.typography.headlineSmall,
            onClick = onDivisionClick,
            modifier = Modifier
                .height(56.dp)
                .weight(1f)
                .testTag("division_button")
        )

        LargeButton(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            text = stringResource(Res.string.mode_all),
            textAlign = TextAlign.Center,
            textStyle = MaterialTheme.typography.headlineSmall,
            onClick = onAllClick,
            modifier = Modifier
                .height(56.dp)
                .weight(1f)
                .testTag("all_button")
        )
    }
}

@Preview
@Composable
private fun ModeSelectorForLandscapePreview() {
    SansuuKidsTheme {
        ModeSelectorForLandscape(
            onAdditionClick = {},
            onSubtractionClick = {},
            onMultiplicationClick = {},
            onDivisionClick = {},
            onAllClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}
