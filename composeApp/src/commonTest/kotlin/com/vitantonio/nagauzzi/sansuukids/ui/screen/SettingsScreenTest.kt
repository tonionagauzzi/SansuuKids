package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
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
    fun ヘッダータイトルが表示される() = runComposeUiTest {
        // Given: 設定画面を表示する
        setContent {
            SansuuKidsTheme {
                SettingsScreen(
                    perQuestionAnswerCheckEnabled = false,
                    hintDisplayEnabled = false,
                    onPerQuestionAnswerCheckChanged = {},
                    onHintDisplayChanged = {},
                    onBackClick = {}
                )
            }
        }

        // When: 画面が表示される

        // Then: ヘッダータイトルが表示される
        onNodeWithTag("settings_title").assertIsDisplayed()
    }

    @Test
    fun スイッチが初期状態でオフを表示する() = runComposeUiTest {
        // Given: 初期状態がfalseの設定画面を表示
        setContent {
            SansuuKidsTheme {
                SettingsScreen(
                    perQuestionAnswerCheckEnabled = false,
                    hintDisplayEnabled = false,
                    onPerQuestionAnswerCheckChanged = {},
                    onHintDisplayChanged = {},
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
                    perQuestionAnswerCheckEnabled = true,
                    hintDisplayEnabled = true,
                    onPerQuestionAnswerCheckChanged = {},
                    onHintDisplayChanged = {},
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
                    perQuestionAnswerCheckEnabled = false,
                    hintDisplayEnabled = false,
                    onPerQuestionAnswerCheckChanged = { changedValue = it },
                    onHintDisplayChanged = {},
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
                    perQuestionAnswerCheckEnabled = true,
                    hintDisplayEnabled = true,
                    onPerQuestionAnswerCheckChanged = { changedValue = it },
                    onHintDisplayChanged = {},
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
                    perQuestionAnswerCheckEnabled = false,
                    hintDisplayEnabled = false,
                    onPerQuestionAnswerCheckChanged = {},
                    onHintDisplayChanged = {},
                    onBackClick = { clicked = true }
                )
            }
        }

        // When: 戻るボタンをクリックする
        onNodeWithTag("back_button").performClick()

        // Then: onBackClickが呼ばれる
        assertTrue(clicked)
    }

    @Test
    fun ヒントスイッチが初期状態でオフを表示する() = runComposeUiTest {
        // Given: ヒント初期状態がfalseの設定画面を表示
        setContent {
            SansuuKidsTheme {
                SettingsScreen(
                    perQuestionAnswerCheckEnabled = false,
                    hintDisplayEnabled = false,
                    onPerQuestionAnswerCheckChanged = {},
                    onHintDisplayChanged = {},
                    onBackClick = {}
                )
            }
        }

        // Then: ヒントスイッチがオフの状態
        onNodeWithTag("hint_display_switch").assertIsOff()
    }

    @Test
    fun ヒントスイッチが初期状態でオンを表示する() = runComposeUiTest {
        // Given: ヒント初期状態がtrueの設定画面を表示
        setContent {
            SansuuKidsTheme {
                SettingsScreen(
                    perQuestionAnswerCheckEnabled = false,
                    hintDisplayEnabled = true,
                    onPerQuestionAnswerCheckChanged = {},
                    onHintDisplayChanged = {},
                    onBackClick = {}
                )
            }
        }

        // Then: ヒントスイッチがオンの状態
        onNodeWithTag("hint_display_switch").assertIsOn()
    }

    @Test
    fun ヒントスイッチをオンにするとコールバックがtrueで呼ばれる() = runComposeUiTest {
        // Given: ヒント初期状態がfalseの設定画面を表示し、変更を追跡する
        var changedValue: Boolean? = null
        setContent {
            SansuuKidsTheme {
                SettingsScreen(
                    perQuestionAnswerCheckEnabled = false,
                    hintDisplayEnabled = false,
                    onPerQuestionAnswerCheckChanged = {},
                    onHintDisplayChanged = { changedValue = it },
                    onBackClick = {}
                )
            }
        }

        // When: ヒントスイッチをクリックしてオンにする
        onNodeWithTag("hint_display_switch").performClick()

        // Then: コールバックがtrueで呼ばれる
        assertEquals(true, changedValue)
    }

    @Test
    fun ヒントスイッチをオフにするとコールバックがfalseで呼ばれる() = runComposeUiTest {
        // Given: ヒント初期状態がtrueの設定画面を表示し、変更を追跡する
        var changedValue: Boolean? = null
        setContent {
            SansuuKidsTheme {
                SettingsScreen(
                    perQuestionAnswerCheckEnabled = false,
                    hintDisplayEnabled = true,
                    onPerQuestionAnswerCheckChanged = {},
                    onHintDisplayChanged = { changedValue = it },
                    onBackClick = {}
                )
            }
        }

        // When: ヒントスイッチをクリックしてオフにする
        onNodeWithTag("hint_display_switch").performClick()

        // Then: コールバックがfalseで呼ばれる
        assertEquals(false, changedValue)
    }
}
