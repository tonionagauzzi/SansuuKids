package com.vitantonio.nagauzzi.sansuukids.ui.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.navigation3.ui.NavDisplay
import com.vitantonio.nagauzzi.sansuukids.data.MedalRepositoryProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.fake.FakeSettingRepository
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.HomeRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.LevelSelectionRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.MedalCollectionRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.ModeSelectionRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.SansuuKidsRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.SettingRoute
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class)
class NavigationIntegrationTest {
    private val medalRepository = MedalRepositoryProvider.medalRepository
    private val settingRepository = FakeSettingRepository()

    @Test
    fun ホーム画面が初期画面として表示される() = runComposeUiTest {
        // Given: ホーム画面を初期画面として設定する
        val backStack = mutableStateListOf<SansuuKidsRoute>(HomeRoute)
        val navigationState = NavigationState(backStack)

        setContent {
            SansuuKidsTheme {
                NavDisplay(
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    backStack = navigationState.entries,
                    onBack = { navigationState.navigateBack() },
                    entryProvider = { key ->
                        navigationEntryProvider(
                            key = key,
                            navigationState = navigationState,
                            medalRepository = medalRepository,
                            settingRepository = settingRepository
                        )
                    }
                )
            }
        }

        // When: 画面が表示される（アクションなし）

        // Then: ホーム画面のボタンが全て表示され、バックスタックにはホーム画面のみが存在する
        onNodeWithTag("start_button").assertIsDisplayed()
        onNodeWithTag("medal_collection_button").assertIsDisplayed()

        assertEquals(1, navigationState.entries.size)
        assertEquals(HomeRoute, navigationState.entries.first())
    }

    @Test
    fun ホーム画面でスタートボタンを押すとモード選択画面に遷移する() = runComposeUiTest {
        // Given: ホーム画面を初期画面として表示する
        val backStack = mutableStateListOf<SansuuKidsRoute>(HomeRoute)
        val navigationState = NavigationState(backStack)

        setContent {
            SansuuKidsTheme {
                NavDisplay(
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    backStack = navigationState.entries,
                    onBack = { navigationState.navigateBack() },
                    entryProvider = { key ->
                        navigationEntryProvider(
                            key = key,
                            navigationState = navigationState,
                            medalRepository = medalRepository,
                            settingRepository = settingRepository
                        )
                    }
                )
            }
        }

        // When: スタートボタンをクリックする
        onNodeWithTag("start_button").performClick()

        // Then: モード選択画面に遷移する
        assertEquals(2, navigationState.entries.size)
        assertEquals(ModeSelectionRoute, navigationState.entries.last())
        onNodeWithTag("addition_button").assertIsDisplayed()
    }

    @Test
    fun ホーム画面で戻るとバックスタックが空になる() = runComposeUiTest {
        // Given: ホーム画面のみがバックスタックに存在する
        val backStack = mutableStateListOf<SansuuKidsRoute>(HomeRoute)
        val navigationState = NavigationState(backStack)

        setContent {
            SansuuKidsTheme {
                NavDisplay(
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    backStack = navigationState.entries,
                    onBack = { navigationState.navigateBack() },
                    entryProvider = { key ->
                        navigationEntryProvider(
                            key = key,
                            navigationState = navigationState,
                            medalRepository = medalRepository,
                            settingRepository = settingRepository
                        )
                    }
                )
            }
        }

        // When: 戻るナビゲーションを実行する
        navigationState.navigateBack()

        // Then: バックスタックが空になる（実際の挙動はnavigation3の責務）
        assertEquals(0, navigationState.entries.size)
    }

    @Test
    fun モード選択画面で戻ると前のホーム画面に戻る() = runComposeUiTest {
        // Given: モード選択画面を表示する
        val backStack = mutableStateListOf(HomeRoute, ModeSelectionRoute)
        val navigationState = NavigationState(backStack)

        setContent {
            SansuuKidsTheme {
                NavDisplay(
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    backStack = navigationState.entries,
                    onBack = { navigationState.navigateBack() },
                    entryProvider = { key ->
                        navigationEntryProvider(
                            key = key,
                            navigationState = navigationState,
                            medalRepository = medalRepository,
                            settingRepository = settingRepository
                        )
                    }
                )
            }
        }

        // When: 戻るナビゲーションを実行する
        navigationState.navigateBack()
        waitForIdle()

        // Then: ホーム画面に戻る
        assertEquals(1, navigationState.entries.size)
        assertEquals(HomeRoute, navigationState.entries.last())
        onNodeWithTag("start_button").assertIsDisplayed()
    }

    @Test
    fun 往復のナビゲーションが正しく動作する() = runComposeUiTest {
        // Given: ホーム画面を初期画面として表示する
        val backStack = mutableStateListOf<SansuuKidsRoute>(HomeRoute)
        val navigationState = NavigationState(backStack)

        setContent {
            SansuuKidsTheme {
                NavDisplay(
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    backStack = navigationState.entries,
                    onBack = { navigationState.navigateBack() },
                    entryProvider = { key ->
                        navigationEntryProvider(
                            key = key,
                            navigationState = navigationState,
                            medalRepository = medalRepository,
                            settingRepository = settingRepository
                        )
                    }
                )
            }
        }

        // When: スタートボタンをクリックして遷移し、戻り、再度遷移する
        onNodeWithTag("start_button").performClick()
        navigationState.navigateBack()
        waitForIdle()
        onNodeWithTag("start_button").performClick()

        // Then: モード選択画面に再度遷移できる
        onNodeWithTag("addition_button").assertIsDisplayed()
        assertEquals(2, navigationState.entries.size)
    }

    @Test
    fun モード選択画面でたしざんを押すとレベル選択画面に遷移する() = runComposeUiTest {
        // Given: モード選択画面を表示する
        val backStack = mutableStateListOf(HomeRoute, ModeSelectionRoute)
        val navigationState = NavigationState(backStack)

        setContent {
            SansuuKidsTheme {
                NavDisplay(
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    backStack = navigationState.entries,
                    onBack = { navigationState.navigateBack() },
                    entryProvider = { key ->
                        navigationEntryProvider(
                            key = key,
                            navigationState = navigationState,
                            medalRepository = medalRepository,
                            settingRepository = settingRepository
                        )
                    }
                )
            }
        }

        // When: たしざんボタンをクリックする
        onNodeWithTag("addition_button").performClick()

        // Then: レベル選択画面に遷移し、モードがADDITIONである
        assertEquals(3, navigationState.entries.size)
        val lastRoute = navigationState.entries.last() as LevelSelectionRoute
        assertEquals(Mode.ADDITION, lastRoute.mode)
        onNodeWithTag("easy_button").assertIsDisplayed()
    }

    @Test
    fun レベル選択画面で戻るとモード選択画面に戻る() = runComposeUiTest {
        // Given: レベル選択画面を表示する
        val backStack = mutableStateListOf(
            HomeRoute,
            ModeSelectionRoute,
            LevelSelectionRoute(Mode.ADDITION)
        )
        val navigationState = NavigationState(backStack)

        setContent {
            SansuuKidsTheme {
                NavDisplay(
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    backStack = navigationState.entries,
                    onBack = { navigationState.navigateBack() },
                    entryProvider = { key ->
                        navigationEntryProvider(
                            key = key,
                            navigationState = navigationState,
                            medalRepository = medalRepository,
                            settingRepository = settingRepository
                        )
                    }
                )
            }
        }

        // When: 戻るナビゲーションを実行する
        navigationState.navigateBack()
        waitForIdle()

        // Then: モード選択画面に戻る
        assertEquals(2, navigationState.entries.size)
        assertEquals(ModeSelectionRoute, navigationState.entries.last())
        onNodeWithTag("addition_button").assertIsDisplayed()
    }

    @Test
    fun ホーム画面でメダルずかんボタンを押すとメダル図鑑画面に遷移する() = runComposeUiTest {
        // Given: ホーム画面を初期画面として表示する
        val backStack = mutableStateListOf<SansuuKidsRoute>(HomeRoute)
        val navigationState = NavigationState(backStack)

        setContent {
            SansuuKidsTheme {
                NavDisplay(
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    backStack = navigationState.entries,
                    onBack = { navigationState.navigateBack() },
                    entryProvider = { key ->
                        navigationEntryProvider(
                            key = key,
                            navigationState = navigationState,
                            medalRepository = medalRepository,
                            settingRepository = settingRepository
                        )
                    }
                )
            }
        }

        // When: メダルずかんボタンをクリックする
        onNodeWithTag("medal_collection_button").performClick()

        // Then: メダル図鑑画面に遷移する
        assertEquals(2, navigationState.entries.size)
        assertEquals(MedalCollectionRoute, navigationState.entries.last())
        onNodeWithTag("medal_collection_title").assertIsDisplayed()
    }

    @Test
    fun メダル図鑑画面で戻るボタンを押すとホーム画面に戻る() = runComposeUiTest {
        // Given: メダル図鑑画面を表示する
        val backStack = mutableStateListOf(HomeRoute, MedalCollectionRoute)
        val navigationState = NavigationState(backStack)

        setContent {
            SansuuKidsTheme {
                NavDisplay(
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    backStack = navigationState.entries,
                    onBack = { navigationState.navigateBack() },
                    entryProvider = { key ->
                        navigationEntryProvider(
                            key = key,
                            navigationState = navigationState,
                            medalRepository = medalRepository,
                            settingRepository = settingRepository
                        )
                    }
                )
            }
        }

        // When: 戻るボタンをクリックする
        onNodeWithTag("back_button").performClick()
        waitForIdle()

        // Then: ホーム画面に戻る
        assertEquals(1, navigationState.entries.size)
        assertEquals(HomeRoute, navigationState.entries.last())
        onNodeWithTag("start_button").assertIsDisplayed()
    }

    @Test
    fun ホーム画面でせっていボタンを押すと設定画面に遷移する() = runComposeUiTest {
        // Given: ホーム画面を初期画面として表示する
        val backStack = mutableStateListOf<SansuuKidsRoute>(HomeRoute)
        val navigationState = NavigationState(backStack)

        setContent {
            SansuuKidsTheme {
                NavDisplay(
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    backStack = navigationState.entries,
                    onBack = { navigationState.navigateBack() },
                    entryProvider = { key ->
                        navigationEntryProvider(
                            key = key,
                            navigationState = navigationState,
                            medalRepository = medalRepository,
                            settingRepository = settingRepository
                        )
                    }
                )
            }
        }

        // When: せっていボタンをクリックする
        onNodeWithTag("settings_button").performClick()

        // Then: 設定画面に遷移する
        assertEquals(2, navigationState.entries.size)
        assertEquals(SettingRoute, navigationState.entries.last())
        onNodeWithTag("settings_title").assertIsDisplayed()
    }

    @Test
    fun 設定画面で戻るボタンを押すとホーム画面に戻る() = runComposeUiTest {
        // Given: 設定画面を表示する
        val backStack = mutableStateListOf(HomeRoute, SettingRoute)
        val navigationState = NavigationState(backStack)

        setContent {
            SansuuKidsTheme {
                NavDisplay(
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    backStack = navigationState.entries,
                    onBack = { navigationState.navigateBack() },
                    entryProvider = { key ->
                        navigationEntryProvider(
                            key = key,
                            navigationState = navigationState,
                            medalRepository = medalRepository,
                            settingRepository = settingRepository
                        )
                    }
                )
            }
        }

        // When: 戻るボタンをクリックする
        onNodeWithTag("back_button").performClick()
        waitForIdle()

        // Then: ホーム画面に戻る
        assertEquals(1, navigationState.entries.size)
        assertEquals(HomeRoute, navigationState.entries.last())
        onNodeWithTag("start_button").assertIsDisplayed()
    }

    @Test
    fun 設定画面で一問ごとの答え合わせがONのときクイズ画面で答え合わせダイアログが表示される() =
        runComposeUiTest {
            // Given: テスト用のリポジトリを作成（デフォルト値trueを使用）
            val backStack = mutableStateListOf<SansuuKidsRoute>(HomeRoute)
            val navigationState = NavigationState(backStack)

            setContent {
                SansuuKidsTheme {
                    NavDisplay(
                        entryDecorators = listOf(
                            rememberSaveableStateHolderNavEntryDecorator(),
                            rememberViewModelStoreNavEntryDecorator()
                        ),
                        backStack = navigationState.entries,
                        onBack = { navigationState.navigateBack() },
                        entryProvider = { key ->
                            navigationEntryProvider(
                                key = key,
                                navigationState = navigationState,
                                medalRepository = medalRepository,
                                settingRepository = settingRepository
                            )
                        }
                    )
                }
            }

            // When: 設定画面に遷移して設定がONであることを確認（デフォルト値）
            onNodeWithTag("settings_button").performClick()
            waitForIdle()

            // Then: 設定がONになっている（デフォルト値）
            runTest { assertEquals(true, settingRepository.perQuestionAnswerCheckEnabled.first()) }

            // When: ホームに戻り、クイズを開始する
            onNodeWithTag("back_button").performClick()
            waitForIdle()
            onNodeWithTag("start_button").performClick()
            waitForIdle()
            onNodeWithTag("addition_button").performClick()
            waitForIdle()
            onNodeWithTag("easy_button").performClick()
            waitForIdle()

            // Then: QuizScreenが表示され、数字を入力して決定ボタンを押すとダイアログが表示される
            onNodeWithTag("question_text").assertIsDisplayed()
            onNodeWithTag("keypad_2").performClick()
            waitForIdle()
            onNodeWithTag("keypad_submit").performClick()
            waitForIdle()

            // Then: 答え合わせダイアログが表示される
            onAllNodesWithTag("result_indicator", useUnmergedTree = true)[0].assertIsDisplayed()
        }

    @Test
    fun 設定画面で一問ごとの答え合わせをOFFにするとクイズ画面で答え合わせダイアログが表示されない() =
        runComposeUiTest {
            // Given: テスト用のリポジトリを作成（デフォルト値trueを使用）
            val backStack = mutableStateListOf<SansuuKidsRoute>(HomeRoute)
            val navigationState = NavigationState(backStack)

            setContent {
                SansuuKidsTheme {
                    NavDisplay(
                        entryDecorators = listOf(
                            rememberSaveableStateHolderNavEntryDecorator(),
                            rememberViewModelStoreNavEntryDecorator()
                        ),
                        backStack = navigationState.entries,
                        onBack = { navigationState.navigateBack() },
                        entryProvider = { key ->
                            navigationEntryProvider(
                                key = key,
                                navigationState = navigationState,
                                medalRepository = medalRepository,
                                settingRepository = settingRepository
                            )
                        }
                    )
                }
            }

            // When: 設定画面に遷移してスイッチをOFFにする（デフォルト値trueからトグル）
            onNodeWithTag("settings_button").performClick()
            waitForIdle()
            onNodeWithTag("per_question_check_switch").performClick()
            waitForIdle()

            // Then: 設定がOFFになる
            runTest { assertEquals(false, settingRepository.perQuestionAnswerCheckEnabled.first()) }

            // When: ホームに戻り、クイズを開始する
            onNodeWithTag("back_button").performClick()
            waitForIdle()
            onNodeWithTag("start_button").performClick()
            waitForIdle()
            onNodeWithTag("addition_button").performClick()
            waitForIdle()
            onNodeWithTag("easy_button").performClick()
            waitForIdle()

            // Then: QuizScreenが表示され、数字を入力して決定ボタンを押してもダイアログは表示されない
            onNodeWithTag("question_text").assertIsDisplayed()
            onNodeWithTag("keypad_2").performClick()
            waitForIdle()
            onNodeWithTag("keypad_submit").performClick()
            waitForIdle()

            // Then: 答え合わせダイアログは表示されない
            onAllNodesWithTag("result_indicator", useUnmergedTree = true)[0].assertDoesNotExist()
        }
}
