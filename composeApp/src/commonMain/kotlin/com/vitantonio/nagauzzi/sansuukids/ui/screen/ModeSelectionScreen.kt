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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
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
import sansuukids.composeapp.generated.resources.mode_selection_title
import sansuukids.composeapp.generated.resources.mode_subtraction

@Composable
fun ModeSelectionScreen(
    onAdditionClick: () -> Unit,
    onSubtractionClick: () -> Unit,
    onMultiplicationClick: () -> Unit,
    onDivisionClick: () -> Unit,
    onAllClick: () -> Unit,
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
            text = stringResource(Res.string.mode_selection_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.testTag("mode_selection_title")
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LargeButton(
                containerColor = MaterialTheme.colorScheme.primary,
                text = stringResource(Res.string.mode_addition),
                textStyle = MaterialTheme.typography.headlineSmall,
                onClick = onAdditionClick,
                modifier = Modifier.height(56.dp).testTag("addition_button")
            )

            LargeButton(
                containerColor = MaterialTheme.colorScheme.secondary,
                text = stringResource(Res.string.mode_subtraction),
                textStyle = MaterialTheme.typography.headlineSmall,
                onClick = onSubtractionClick,
                modifier = Modifier.height(56.dp).testTag("subtraction_button")
            )

            LargeButton(
                containerColor = MaterialTheme.colorScheme.tertiary,
                text = stringResource(Res.string.mode_multiplication),
                textStyle = MaterialTheme.typography.headlineSmall,
                onClick = onMultiplicationClick,
                modifier = Modifier.height(56.dp).testTag("multiplication_button")
            )

            LargeButton(
                containerColor = Color(0xFF9C27B0),
                text = stringResource(Res.string.mode_division),
                textStyle = MaterialTheme.typography.headlineSmall,
                onClick = onDivisionClick,
                modifier = Modifier.height(56.dp).testTag("division_button")
            )

            LargeButton(
                containerColor = Color(0xFFE91E63),
                text = stringResource(Res.string.mode_all),
                textStyle = MaterialTheme.typography.headlineSmall,
                onClick = onAllClick,
                modifier = Modifier.height(56.dp).testTag("all_button")
            )
        }
    }
}

@Preview
@Composable
private fun ModeSelectionScreenPreview() {
    SansuuKidsTheme {
        ModeSelectionScreen(
            onAdditionClick = {},
            onSubtractionClick = {},
            onMultiplicationClick = {},
            onDivisionClick = {},
            onAllClick = {}
        )
    }
}
