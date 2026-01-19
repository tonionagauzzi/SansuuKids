package com.vitantonio.nagauzzi.sansuukids.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun GridCell(
    text: String,
    isHeader: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(0.5.dp, MaterialTheme.colorScheme.outline)
            .background(
                if (isHeader) MaterialTheme.colorScheme.surfaceVariant
                else MaterialTheme.colorScheme.surface
            )
            .padding(8.dp)
            .size(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = if (isHeader) MaterialTheme.typography.bodySmall else MaterialTheme.typography.bodyMedium,
            fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
    }
}
