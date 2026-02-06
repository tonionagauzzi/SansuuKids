package com.vitantonio.nagauzzi.sansuukids.ui.component.medal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.model.MedalDisplay
import com.vitantonio.nagauzzi.sansuukids.model.emojiRes
import com.vitantonio.nagauzzi.sansuukids.model.labelRes
import org.jetbrains.compose.resources.stringResource
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.medal_detail_count
import sansuukids.composeapp.generated.resources.medal_detail_title

@Composable
internal fun MedalDetailDialog(
    medalDisplay: MedalDisplay,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .clickable { onDismiss() }
                .testTag("medal_detail_dialog"),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(
                        Res.string.medal_detail_title,
                        stringResource(medalDisplay.mode.labelRes),
                        stringResource(medalDisplay.level.labelRes)
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.testTag("medal_detail_title")
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val medals = listOf(Medal.Gold, Medal.Silver, Medal.Bronze, Medal.Star)
                    medals.forEach { medal ->
                        val count = medalDisplay.counts.getCountForMedal(medal)
                        MedalCountItem(
                            emoji = stringResource(medal.emojiRes),
                            count = count,
                            isActive = count > 0,
                            modifier = Modifier.testTag("medal_count_${medal.name.lowercase()}")
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MedalCountItem(
    emoji: String,
    count: Int,
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = emoji,
            style = MaterialTheme.typography.headlineMedium,
            color = if (isActive) {
                MaterialTheme.colorScheme.onSurface
            } else {
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            }
        )
        Text(
            text = stringResource(Res.string.medal_detail_count, count),
            style = MaterialTheme.typography.bodyMedium,
            color = if (isActive) {
                MaterialTheme.colorScheme.onSurface
            } else {
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            }
        )
    }
}
