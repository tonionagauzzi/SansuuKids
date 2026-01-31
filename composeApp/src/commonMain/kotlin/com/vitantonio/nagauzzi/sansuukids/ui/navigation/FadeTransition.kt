package com.vitantonio.nagauzzi.sansuukids.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith

private const val TRANSITION_DURATION_MS = 300

internal fun fadeTransition() =
    fadeIn(animationSpec = tween(durationMillis = TRANSITION_DURATION_MS)) togetherWith
            fadeOut(animationSpec = tween(durationMillis = TRANSITION_DURATION_MS))
