package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.vitantonio.nagauzzi.sansuukids.model.Medal
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class ResultScreenTest {

    @Test
    fun リトライボタンを押すとonRetryClickが呼ばれる() = runComposeUiTest {
        // Given: 結果画面を表示し、クリック状態を追跡する
        var clicked = false
        setContent {
            SansuuKidsTheme {
                ResultScreen(
                    score = 80,
                    medal = Medal.Silver,
                    onRetryClick = { clicked = true },
                    onHomeClick = {}
                )
            }
        }

        // When: リトライボタンをクリックする
        onNodeWithTag("retry_button").performClick()

        // Then: onRetryClickが呼ばれる
        assertTrue(clicked)
    }

    @Test
    fun ホームボタンを押すとonHomeClickが呼ばれる() = runComposeUiTest {
        // Given: 結果画面を表示し、クリック状態を追跡する
        var clicked = false
        setContent {
            SansuuKidsTheme {
                ResultScreen(
                    score = 80,
                    medal = Medal.Silver,
                    onRetryClick = {},
                    onHomeClick = { clicked = true }
                )
            }
        }

        // When: ホームボタンをクリックする
        onNodeWithTag("home_button").performClick()

        // Then: onHomeClickが呼ばれる
        assertTrue(clicked)
    }
}
