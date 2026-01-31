package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.ui.component.result.ResultContent
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ResultScreen(
    score: Int,
    medal: Medal,
    onCheckAnswersClick: () -> Unit,
    onRetryClick: () -> Unit,
    onHomeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .safeContentPadding()
            .fillMaxSize()
    ) {
        ResultContent(
            isLandscape = maxWidth > maxHeight,
            score = score,
            medal = medal,
            onCheckAnswersClick = onCheckAnswersClick,
            onRetryClick = onRetryClick,
            onHomeClick = onHomeClick
        )
    }
}

@Preview(widthDp = 360, heightDp = 640) // 縦画面
@Preview(widthDp = 640, heightDp = 360) // 横画面
@Preview(widthDp = 480, heightDp = 480) // 正方形画面
@Preview(widthDp = 481, heightDp = 480) // 僅かに横画面
@Composable
private fun ResultScreenPreview() {
    SansuuKidsTheme {
        ResultScreen(
            score = 80,
            medal = Medal.Silver,
            onCheckAnswersClick = {},
            onRetryClick = {},
            onHomeClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}
