package com.vitantonio.nagauzzi.sansuukids.ui.navigation

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.vitantonio.nagauzzi.sansuukids.data.MedalRepository
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.AnswerCheckRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.HomeRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.LevelSelectionRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.MedalCollectionRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.ModeSelectionRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.QuizRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.ResultRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.SansuuKidsRoute
import com.vitantonio.nagauzzi.sansuukids.ui.viewmodel.QuizViewModel
import com.vitantonio.nagauzzi.sansuukids.ui.screen.AnswerCheckScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.HomeScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.LevelSelectionScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.MedalCollectionScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.ModeSelectionScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.QuizScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.ResultScreen

internal fun navigationEntryProvider(
    key: SansuuKidsRoute,
    navigationState: NavigationState,
    medalRepository: MedalRepository = MedalRepository()
): NavEntry<SansuuKidsRoute> {
    return when (key) {
        HomeRoute -> NavEntry(key) {
            HomeScreen(
                onStartClick = { navigationState.navigateTo(ModeSelectionRoute) },
                onMedalCollectionClick = { navigationState.navigateTo(MedalCollectionRoute) }
            )
        }

        MedalCollectionRoute -> NavEntry(key) {
            MedalCollectionScreen(
                getMedal = medalRepository::getMedal,
                onBackClick = { navigationState.navigateBack() }
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
                    medalRepository.saveMedal(
                        mode = key.mode,
                        level = key.level,
                        medal = viewModel.earnedMedal
                    )
                    navigationState.navigateTo(
                        ResultRoute(
                            mode = key.mode,
                            level = key.level,
                            score = viewModel.earnedScore,
                            medal = viewModel.earnedMedal,
                            questions = quizState.totalQuestions,
                            userAnswers = quizState.userAnswers
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
                onBackClick = {
                    if (quizState.answeredCount > 0) {
                        viewModel.cancelLastAnswer()
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
                onCheckAnswersClick = {
                    navigationState.navigateTo(
                        AnswerCheckRoute(
                            questions = key.questions,
                            userAnswers = key.userAnswers
                        )
                    )
                },
                onRetryClick = {
                    navigationState.popToHome()
                    navigationState.navigateTo(ModeSelectionRoute)
                },
                onHomeClick = {
                    navigationState.popToHome()
                }
            )
        }

        is AnswerCheckRoute -> NavEntry(key) {
            AnswerCheckScreen(
                questions = key.questions,
                userAnswers = key.userAnswers,
                onFinishClick = {
                    navigationState.navigateBack()
                }
            )
        }
    }
}
