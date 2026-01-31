package com.vitantonio.nagauzzi.sansuukids.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.arrow_back
import sansuukids.composeapp.generated.resources.quiz_back

@Composable
internal fun AppHeader(
    title: String,
    isMultiLine: Boolean,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Column(modifier = modifier) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
            Button(
                onClick = onBackClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                ),
                modifier = Modifier
                    .height(64.dp)
                    .testTag("back_button"),
                shape = RoundedCornerShape(12.dp),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.arrow_back),
                    contentDescription = stringResource(Res.string.quiz_back),
                    modifier = Modifier.size(32.dp)
                )
            }

            if (!isMultiLine) {
                AppHeaderTitle(title = title)
            }
        }

        if (isMultiLine) {
            AppHeaderTitle(title = title, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Composable
private fun AppHeaderTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun AppHeaderPreview() {
    SansuuKidsTheme {
        AppHeader(
            title = "せってい",
            isMultiLine = false,
            onBackClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}

@Preview
@Composable
private fun AppHeaderMultilinePreview() {
    SansuuKidsTheme {
        AppHeader(
            title = "せってい",
            isMultiLine = true,
            onBackClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}
