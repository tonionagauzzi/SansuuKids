package com.vitantonio.nagauzzi.sansuukids.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.model.QuestionGenerator
import com.vitantonio.nagauzzi.sansuukids.model.QuizState

internal class QuizViewModel(
    mode: Mode,
    level: Level
) : ViewModel() {
    val quizState = QuizState(QuestionGenerator.generateQuiz(mode, level))
}
