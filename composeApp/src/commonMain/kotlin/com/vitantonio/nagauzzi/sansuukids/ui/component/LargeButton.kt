package com.vitantonio.nagauzzi.sansuukids.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LargeButton(
    text: String,
    textStyle: TextStyle,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        ),
        contentPadding = PaddingValues(4.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            textAlign = textAlign,
            maxLines = 2,
            style = textStyle
        )
    }
}

@Preview(widthDp = 120)
@Composable
private fun LargeButtonPreview() {
    SansuuKidsTheme {
        LargeButton(
            text = "テキスト",
            textStyle = MaterialTheme.typography.headlineSmall,
            onClick = {},
            textAlign = TextAlign.Center,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Preview(widthDp = 120)
@Composable
private fun LargeButtonLongTextPreview() {
    SansuuKidsTheme {
        LargeButton(
            text = "長めのテキスト",
            textStyle = MaterialTheme.typography.headlineSmall,
            onClick = {},
            textAlign = TextAlign.Start,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}
