package com.vitantonio.nagauzzi.sansuukids.ui.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.HomeRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.LevelSelectionRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.ModeSelectionRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.QuizRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.SansuuKidsRoute
import com.vitantonio.nagauzzi.sansuukids.ui.viewmodel.QuizViewModel
import com.vitantonio.nagauzzi.sansuukids.ui.screen.HomeScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.LevelSelectionScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.ModeSelectionScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.QuizScreen

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
                onEasyClick = { navigationState.navigateTo(QuizRoute(key.mode, Level.EASY)) },
                onNormalClick = { navigationState.navigateTo(QuizRoute(key.mode, Level.NORMAL)) },
                onDifficultClick = { navigationState.navigateTo(QuizRoute(key.mode, Level.DIFFICULT)) }
            )
        }

        is QuizRoute -> NavEntry(key) {
            val viewModel = viewModel(key = "${key.mode}_${key.level}") {
                QuizViewModel(key.mode, key.level)
            }

            QuizScreen(
                quizState = viewModel.quizState,
                onDigitClick = { digit -> viewModel.quizState.appendDigit(digit) },
                onDeleteClick = { viewModel.quizState.deleteLastDigit() },
                onSubmitClick = {
                    viewModel.quizState.submitAnswer()
                    if (viewModel.quizState.isQuizComplete) {
                        // TODO: Navigate to ResultScreen with viewModel.quizState.toResult()
                    }
                },
                onCancelClick = {
                    if (viewModel.quizState.answeredCount > 0) {
                        // TODO: Navigate to ResultScreen with partial result
                    } else {
                        navigationState.popToHome()
                    }
                }
            )
        }
    }
}
