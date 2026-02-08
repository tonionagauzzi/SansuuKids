package com.vitantonio.nagauzzi.sansuukids.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.vitantonio.nagauzzi.sansuukids.data.MedalRepository
import com.vitantonio.nagauzzi.sansuukids.data.SettingRepository
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
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.SettingRoute
import com.vitantonio.nagauzzi.sansuukids.ui.viewmodel.QuizViewModel
import com.vitantonio.nagauzzi.sansuukids.ui.screen.AnswerCheckScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.HomeScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.LevelSelectionScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.MedalCollectionScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.ModeSelectionScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.QuizScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.ResultScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.SettingScreen
import kotlinx.coroutines.launch

internal fun navigationEntryProvider(
    key: SansuuKidsRoute,
    navigationState: NavigationState,
    medalRepository: MedalRepository,
    settingRepository: SettingRepository
): NavEntry<SansuuKidsRoute> {
    return when (key) {
        HomeRoute -> NavEntry(key) {
            HomeScreen(
                onStartClick = { navigationState.navigateTo(ModeSelectionRoute) },
                onMedalCollectionClick = { navigationState.navigateTo(MedalCollectionRoute) },
                onSettingClick = { navigationState.navigateTo(SettingRoute) }
            )
        }

        MedalCollectionRoute -> NavEntry(key) {
            val medalCounters by medalRepository.medalCounters.collectAsStateWithLifecycle(emptyList())
            MedalCollectionScreen(
                medalCounters = medalCounters,
                onBackClick = { navigationState.navigateBack() }
            )
        }

        SettingRoute -> NavEntry(key) {
            val scope = rememberCoroutineScope()
            val perQuestionAnswerCheckEnabled by settingRepository.perQuestionAnswerCheckEnabled
                .collectAsStateWithLifecycle(true)
            val hintDisplayEnabled by settingRepository.hintDisplayEnabled
                .collectAsStateWithLifecycle(true)
            SettingScreen(
                perQuestionAnswerCheckEnabled = perQuestionAnswerCheckEnabled,
                hintDisplayEnabled = hintDisplayEnabled,
                onPerQuestionAnswerCheckChanged = { enabled ->
                    scope.launch {
                        settingRepository.setPerQuestionAnswerCheckEnabled(enabled)
                    }
                },
                onHintDisplayChanged = { enabled ->
                    scope.launch {
                        settingRepository.setHintDisplayEnabled(enabled)
                    }
                },
                onBackClick = { navigationState.navigateBack() }
            )
        }

        ModeSelectionRoute -> NavEntry(key) {
            ModeSelectionScreen(
                onAdditionClick = { navigationState.navigateTo(LevelSelectionRoute(Mode.Addition)) },
                onSubtractionClick = { navigationState.navigateTo(LevelSelectionRoute(Mode.Subtraction)) },
                onMultiplicationClick = { navigationState.navigateTo(LevelSelectionRoute(Mode.Multiplication)) },
                onDivisionClick = { navigationState.navigateTo(LevelSelectionRoute(Mode.Division)) },
                onAllClick = { navigationState.navigateTo(LevelSelectionRoute(Mode.All)) },
                onBackClick = { navigationState.navigateBack() }
            )
        }

        is LevelSelectionRoute -> NavEntry(key) {
            LevelSelectionScreen(
                onEasyClick = { navigationState.navigateTo(QuizRoute(key.mode, Level.Easy)) },
                onNormalClick = { navigationState.navigateTo(QuizRoute(key.mode, Level.Normal)) },
                onDifficultClick = {
                    navigationState.navigateTo(
                        QuizRoute(
                            key.mode,
                            Level.Difficult
                        )
                    )
                },
                onBackClick = { navigationState.navigateBack() }
            )
        }

        is QuizRoute -> NavEntry(key) {
            val viewModel = viewModel {
                QuizViewModel(mode = key.mode, level = key.level)
            }
            val quizState by viewModel.quizState.collectAsStateWithLifecycle()

            LaunchedEffect(quizState.isQuizComplete) {
                if (quizState.isQuizComplete) {
                    medalRepository.add(
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
                            questions = quizState.quiz.questions,
                            userAnswers = quizState.userAnswers
                        )
                    )
                }
            }

            val perQuestionAnswerCheckEnabled by settingRepository.perQuestionAnswerCheckEnabled
                .collectAsStateWithLifecycle(true)
            val hintDisplayEnabled by settingRepository.hintDisplayEnabled
                .collectAsStateWithLifecycle(true)

            QuizScreen(
                quizState = quizState,
                perQuestionAnswerCheckEnabled = perQuestionAnswerCheckEnabled,
                hintDisplayEnabled = hintDisplayEnabled && key.level == Level.Easy,
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
