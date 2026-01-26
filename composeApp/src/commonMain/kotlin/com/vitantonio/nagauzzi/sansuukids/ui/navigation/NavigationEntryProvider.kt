package com.vitantonio.nagauzzi.sansuukids.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.vitantonio.nagauzzi.sansuukids.data.MedalRepository
import com.vitantonio.nagauzzi.sansuukids.data.SettingsRepository
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.MedalDisplay
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.AnswerCheckRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.HomeRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.LevelSelectionRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.MedalCollectionRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.ModeSelectionRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.QuizRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.ResultRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.SansuuKidsRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.SettingsRoute
import com.vitantonio.nagauzzi.sansuukids.ui.viewmodel.QuizViewModel
import com.vitantonio.nagauzzi.sansuukids.ui.screen.AnswerCheckScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.HomeScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.LevelSelectionScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.MedalCollectionScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.ModeSelectionScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.QuizScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.ResultScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.SettingsScreen
import kotlinx.coroutines.launch

internal fun navigationEntryProvider(
    key: SansuuKidsRoute,
    navigationState: NavigationState,
    medalRepository: MedalRepository = MedalRepository(),
    settingsRepository: SettingsRepository = SettingsRepository()
): NavEntry<SansuuKidsRoute> {
    return when (key) {
        HomeRoute -> NavEntry(key) {
            HomeScreen(
                onStartClick = { navigationState.navigateTo(ModeSelectionRoute) },
                onMedalCollectionClick = { navigationState.navigateTo(MedalCollectionRoute) },
                onSettingsClick = { navigationState.navigateTo(SettingsRoute) }
            )
        }

        MedalCollectionRoute -> NavEntry(key) {
            val medalDisplays by medalRepository.medalDisplays.collectAsStateWithLifecycle(emptyList())
            MedalCollectionScreen(
                medalDisplays = medalDisplays,
                onBackClick = { navigationState.navigateBack() }
            )
        }

        SettingsRoute -> NavEntry(key) {
            val scope = rememberCoroutineScope()
            val perQuestionAnswerCheckEnabled by settingsRepository.perQuestionAnswerCheckEnabled
                .collectAsStateWithLifecycle(true)
            val hintDisplayEnabled by settingsRepository.hintDisplayEnabled
                .collectAsStateWithLifecycle(true)
            SettingsScreen(
                perQuestionAnswerCheckEnabled = perQuestionAnswerCheckEnabled,
                hintDisplayEnabled = hintDisplayEnabled,
                onPerQuestionAnswerCheckChanged = { enabled ->
                    scope.launch {
                        settingsRepository.setPerQuestionAnswerCheckEnabled(enabled)
                    }
                },
                onHintDisplayChanged = { enabled ->
                    scope.launch {
                        settingsRepository.setHintDisplayEnabled(enabled)
                    }
                },
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
            val viewModel = viewModel {
                QuizViewModel(mode = key.mode, level = key.level)
            }
            val quizState by viewModel.quizState.collectAsStateWithLifecycle()

            LaunchedEffect(quizState.isQuizComplete) {
                if (quizState.isQuizComplete) {
                    medalRepository.save(
                        MedalDisplay(
                            mode = key.mode,
                            level = key.level,
                            medal = viewModel.earnedMedal
                        )
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

            val perQuestionAnswerCheckEnabled by settingsRepository.perQuestionAnswerCheckEnabled
                .collectAsStateWithLifecycle(true)
            val hintDisplayEnabled by settingsRepository.hintDisplayEnabled
                .collectAsStateWithLifecycle(true)

            QuizScreen(
                quizState = quizState,
                perQuestionAnswerCheckEnabled = perQuestionAnswerCheckEnabled,
                hintDisplayEnabled = hintDisplayEnabled && key.level == Level.EASY,
                onDigitClick = { digit -> viewModel.appendDigit(digit) },
                onDeleteClick = { viewModel.deleteLastDigit() },
                onSubmitClick = { viewModel.submitAnswer() },
                onBackClick = {
                    if (quizState.answeredCount > 0) {
                        viewModel.cancelLastAnswer()
                    } else {
                        navigationState.navigateBack()
                    }
                }
            )
        }

        is ResultRoute -> NavEntry(key) {
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
                onBackClick = {
                    navigationState.navigateBack()
                },
                onFinishClick = {
                    navigationState.popToHome()
                }
            )
        }
    }
}
