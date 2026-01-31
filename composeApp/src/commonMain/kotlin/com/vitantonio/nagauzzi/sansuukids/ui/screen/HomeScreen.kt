package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vitantonio.nagauzzi.sansuukids.ui.component.home.HomeContent
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(
    onStartClick: () -> Unit,
    onMedalCollectionClick: () -> Unit,
    onSettingClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .safeContentPadding()
            .fillMaxSize()
    ) {
        HomeContent(
            isLandscape = maxWidth > maxHeight,
            onStartClick = onStartClick,
            onMedalCollectionClick = onMedalCollectionClick,
            onSettingClick = onSettingClick
        )
    }
}

@Preview(widthDp = 360, heightDp = 640) // 縦画面
@Preview(widthDp = 640, heightDp = 360) // 横画面
@Preview(widthDp = 480, heightDp = 480) // 正方形画面
@Preview(widthDp = 481, heightDp = 480) // 僅かに横画面
@Composable
private fun HomeScreenPreview() {
    SansuuKidsTheme {
        HomeScreen(
            onStartClick = {},
            onMedalCollectionClick = {},
            onSettingClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}
