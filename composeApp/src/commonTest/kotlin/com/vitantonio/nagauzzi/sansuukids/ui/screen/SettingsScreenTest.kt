package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class SettingsScreenTest {

    @Test
    fun スイッチが初期状態でオフを表示する() = runComposeUiTest {
        // Given: 初期状態がfalseの設定画面を表示
        setContent {
            SansuuKidsTheme {
                SettingsScreen(
                    initialPerQuestionAnswerCheckEnabled = false,
                    onPerQuestionAnswerCheckChanged = {},
                    onBackClick = {}
                )
            }
        }

        // Then: スイッチがオフの状態
        onNodeWithTag("per_question_check_switch").assertIsOff()
    }

    @Test
    fun スイッチが初期状態でオンを表示する() = runComposeUiTest {
        // Given: 初期状態がtrueの設定画面を表示
        setContent {
            SansuuKidsTheme {
                SettingsScreen(
                    initialPerQuestionAnswerCheckEnabled = true,
                    onPerQuestionAnswerCheckChanged = {},
                    onBackClick = {}
                )
            }
        }

        // Then: スイッチがオンの状態
        onNodeWithTag("per_question_check_switch").assertIsOn()
    }

    @Test
    fun スイッチをオンにするとコールバックがtrueで呼ばれる() = runComposeUiTest {
        // Given: 初期状態がfalseの設定画面を表示し、変更を追跡する
        var changedValue: Boolean? = null
        setContent {
            SansuuKidsTheme {
                SettingsScreen(
                    initialPerQuestionAnswerCheckEnabled = false,
                    onPerQuestionAnswerCheckChanged = { changedValue = it },
                    onBackClick = {}
                )
            }
        }

        // When: スイッチをクリックしてオンにする
        onNodeWithTag("per_question_check_switch").performClick()

        // Then: コールバックがtrueで呼ばれる
        assertEquals(true, changedValue)
    }

    @Test
    fun スイッチをオフにするとコールバックがfalseで呼ばれる() = runComposeUiTest {
        // Given: 初期状態がtrueの設定画面を表示し、変更を追跡する
        var changedValue: Boolean? = null
        setContent {
            SansuuKidsTheme {
                SettingsScreen(
                    initialPerQuestionAnswerCheckEnabled = true,
                    onPerQuestionAnswerCheckChanged = { changedValue = it },
                    onBackClick = {}
                )
            }
        }

        // When: スイッチをクリックしてオフにする
        onNodeWithTag("per_question_check_switch").performClick()

        // Then: コールバックがfalseで呼ばれる
        assertEquals(false, changedValue)
    }

    @Test
    fun 戻るボタンを押すとonBackClickが呼ばれる() = runComposeUiTest {
        // Given: 設定画面を表示し、クリック状態を追跡する
        var clicked = false
        setContent {
            SansuuKidsTheme {
                SettingsScreen(
                    initialPerQuestionAnswerCheckEnabled = false,
                    onPerQuestionAnswerCheckChanged = {},
                    onBackClick = { clicked = true }
                )
            }
        }

        // When: 戻るボタンをクリックする
        onNodeWithTag("settings_back_button").performClick()

        // Then: onBackClickが呼ばれる
        assertTrue(clicked)
    }
}
