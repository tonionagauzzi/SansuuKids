package com.vitantonio.nagauzzi.sansuukids.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.vitantonio.nagauzzi.sansuukids.data.DifficultyRepository
import com.vitantonio.nagauzzi.sansuukids.data.MedalRepository
import com.vitantonio.nagauzzi.sansuukids.data.SettingRepository
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.AnswerCheckRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.DifficultyAdjustmentRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.HomeRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.LevelSelectionRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.MedalCollectionRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.OperationTypeSelectionRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.QuizRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.ResultRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.SansuuKidsRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.SettingRoute
import com.vitantonio.nagauzzi.sansuukids.ui.viewmodel.QuizViewModel
import com.vitantonio.nagauzzi.sansuukids.ui.screen.AnswerCheckScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.DifficultyAdjustmentScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.HomeScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.LevelSelectionScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.MedalCollectionScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.OperationTypeSelectionScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.QuizScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.ResultScreen
import com.vitantonio.nagauzzi.sansuukids.ui.screen.SettingScreen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

internal fun navigationEntryProvider(
    key: SansuuKidsRoute,
    navigationState: NavigationState,
    medalRepository: MedalRepository,
    settingRepository: SettingRepository,
    difficultyRepository: DifficultyRepository
): NavEntry<SansuuKidsRoute> {
    return when (key) {
        HomeRoute -> NavEntry(key) {
            HomeScreen(
                onStartClick = { navigationState.navigateTo(OperationTypeSelectionRoute) },
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

        OperationTypeSelectionRoute -> NavEntry(key) {
            OperationTypeSelectionScreen(
                onAdditionClick = {
                    navigationState.navigateTo(LevelSelectionRoute(OperationType.Addition))
                },
                onSubtractionClick = {
                    navigationState.navigateTo(LevelSelectionRoute(OperationType.Subtraction))
                },
                onMultiplicationClick = {
                    navigationState.navigateTo(LevelSelectionRoute(OperationType.Multiplication))
                },
                onDivisionClick = {
                    navigationState.navigateTo(LevelSelectionRoute(OperationType.Division))
                },
                onAllClick = {
                    navigationState.navigateTo(LevelSelectionRoute(OperationType.All))
                },
                onBackClick = { navigationState.navigateBack() }
            )
        }

        is LevelSelectionRoute -> NavEntry(key) {
            val scope = rememberCoroutineScope()

            LevelSelectionScreen(
                isEnabledSetting = key.operationType != OperationType.All,
                onClick = { level ->
                    scope.launch {
                        navigationState.navigateTo(
                            QuizRoute(
                                quizRange = difficultyRepository.getQuizRange(
                                    operationType = key.operationType,
                                    level = level
                                ).first()
                            )
                        )
                    }
                },
                onSettingClick = { level ->
                    navigationState.navigateTo(
                        DifficultyAdjustmentRoute(
                            operationType = key.operationType,
                            level = level
                        )
                    )
                },
                onBackClick = { navigationState.navigateBack() }
            )
        }

        is DifficultyAdjustmentRoute -> NavEntry(key) {
            val scope = rememberCoroutineScope()
            val operationType = key.operationType
            val level = key.level
            val quizRange by difficultyRepository.getQuizRange(operationType, level)
                .collectAsStateWithLifecycle(initialValue = QuizRange.Default(operationType, level))

            DifficultyAdjustmentScreen(
                quizRange = quizRange,
                onQuizRangeChanged = { newRange ->
                    scope.launch {
                        difficultyRepository.set(newRange)
                    }
                },
                onReset = { operationType ->
                    scope.launch {
                        difficultyRepository.resetToDefault(operationType, level)
                    }
                },
                onBackClick = { navigationState.navigateBack() }
            )
        }

        is QuizRoute -> NavEntry(key) {
            val viewModel = viewModel { QuizViewModel(quizRange = key.quizRange) }
            val operationType = key.quizRange.operationType
            val level = key.quizRange.level
            val quizState by viewModel.quizState.collectAsStateWithLifecycle()

            LaunchedEffect(quizState.isQuizComplete) {
                if (quizState.isQuizComplete) {
                    medalRepository.add(
                        operationType = operationType,
                        level = level,
                        medal = viewModel.earnedMedal
                    )
                    navigationState.navigateTo(
                        ResultRoute(
                            operationType = operationType,
                            level = level,
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
                hintDisplayEnabled = hintDisplayEnabled && level == Level.Easy,
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
                    navigationState.navigateTo(OperationTypeSelectionRoute)
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
