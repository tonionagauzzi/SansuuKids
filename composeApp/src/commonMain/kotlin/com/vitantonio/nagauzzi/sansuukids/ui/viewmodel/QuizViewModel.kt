package com.vitantonio.nagauzzi.sansuukids.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.vitantonio.nagauzzi.sansuukids.logic.GenerateQuiz
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.model.QuizState

internal class QuizViewModel(
    mode: Mode,
    level: Level
) : ViewModel() {
    private val generateQuiz = GenerateQuiz()
    val quizState = QuizState(generateQuiz(mode, level))
}
