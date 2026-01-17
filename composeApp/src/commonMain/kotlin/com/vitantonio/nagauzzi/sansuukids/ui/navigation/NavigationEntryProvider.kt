package com.vitantonio.nagauzzi.sansuukids.ui.navigation

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.HomeRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.LevelSelectionRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.ModeSelectionRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.QuizRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.ResultRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.SansuuKidsRoute
import com.vitantonio.nagauzzi.sansuukids.ui.viewmodel.QuizViewModel
import com.vitantonio.nagauzzi.sansuukids.ui.screen.HomeScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.LevelSelectionScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.ModeSelectionScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.QuizScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.ResultScreen

internal fun navigationEntryProvider(
    key: SansuuKidsRoute,
    navigationState: NavigationState
): NavEntry<SansuuKidsRoute> {
    return when (key) {
        HomeRoute -> NavEntry(key) {
            HomeScreen(
                onStartClick = { navigationState.navigateTo(ModeSelectionRoute) },
                onMedalCollectionClick = { /* TODO: Navigate to Medal Collection */ },
                onSettingsClick = { /* TODO: Navigate to Settings */ }
            )
        }

        ModeSelectionRoute -> NavEntry(key) {
            ModeSelectionScreen(
                onAdditionClick = { navigationState.navigateTo(LevelSelectionRoute(Mode.ADDITION)) },
                onSubtractionClick = { navigationState.navigateTo(LevelSelectionRoute(Mode.SUBTRACTION)) },
                onMultiplicationClick = { navigationState.navigateTo(LevelSelectionRoute(Mode.MULTIPLICATION)) },
                onDivisionClick = { navigationState.navigateTo(LevelSelectionRoute(Mode.DIVISION)) },
                onAllClick = { navigationState.navigateTo(LevelSelectionRoute(Mode.ALL)) }
            )
        }

        is LevelSelectionRoute -> NavEntry(key) {
            LevelSelectionScreen(
                onEasyClick = {
                    navigationState.navigateTo(
                        QuizRoute(
                            key.mode,
                            Level.EASY
                        )
                    )
                },
                onNormalClick = {
                    navigationState.navigateTo(
                        QuizRoute(
                            key.mode,
                            Level.NORMAL
                        )
                    )
                },
                onDifficultClick = {
                    navigationState.navigateTo(
                        QuizRoute(
                            key.mode,
                            Level.DIFFICULT
                        )
                    )
                }
            )
        }

        is QuizRoute -> NavEntry(key) {
            val viewModel = viewModel(key = "${key.mode.name}_${key.level.name}") {
                QuizViewModel(key.mode, key.level)
            }
            val quizState by viewModel.quizState.collectAsState()

            LaunchedEffect(quizState.isQuizComplete) {
                if (quizState.isQuizComplete) {
                    navigationState.navigateTo(
                        ResultRoute(
                            score = viewModel.earnedScore,
                            medal = viewModel.earnedMedal
                        )
                    )
                }
            }

            QuizScreen(
                quizState = quizState,
                onDigitClick = { digit -> viewModel.appendDigit(digit) },
                onDeleteClick = { viewModel.deleteLastDigit() },
                onSubmitClick = {
                    viewModel.submitAnswer()
                },
                onCancelClick = {
                    if (quizState.answeredCount > 0) {
                        navigationState.navigateTo(
                            ResultRoute(
                                score = viewModel.earnedScore,
                                medal = viewModel.earnedMedal
                            )
                        )
                    } else {
                        navigationState.popToHome()
                    }
                }
            )
        }

        is ResultRoute -> NavEntry(key) {
            val viewModelStoreOwner = LocalViewModelStoreOwner.current

            DisposableEffect(Unit) {
                onDispose {
                    // 結果画面が破棄される時にViewModelStoreをクリア
                    viewModelStoreOwner?.viewModelStore?.clear()
                }
            }

            ResultScreen(
                score = key.score,
                medal = key.medal,
                onRetryClick = {
                    navigationState.popToHome()
                    navigationState.navigateTo(ModeSelectionRoute)
                },
                onHomeClick = {
                    navigationState.popToHome()
                }
            )
        }
    }
}
