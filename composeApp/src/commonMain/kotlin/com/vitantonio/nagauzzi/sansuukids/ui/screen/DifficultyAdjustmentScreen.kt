package com.vitantonio.nagauzzi.sansuukids.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import com.vitantonio.nagauzzi.sansuukids.model.labelRes
import com.vitantonio.nagauzzi.sansuukids.ui.component.AppHeader
import com.vitantonio.nagauzzi.sansuukids.ui.component.levelselection.DifficultyAdjustmentContent
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.difficulty_adjustment_title
import sansuukids.composeapp.generated.resources.difficulty_reset

@Composable
internal fun DifficultyAdjustmentScreen(
    level: Level,
    operationType: OperationType,
    quizRange: QuizRange,
    onRangeChanged: (OperationType, Int, Int) -> Unit,
    onReset: (OperationType) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .safeContentPadding()
            .fillMaxSize()
    ) {
        val isLandscape = maxWidth > maxHeight

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            AppHeader(
                title = stringResource(
                    Res.string.difficulty_adjustment_title,
                    stringResource(level.labelRes)
                ),
                actionString = stringResource(Res.string.difficulty_reset),
                isMultiLine = !isLandscape,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("difficulty_adjustment_title"),
                onBackClick = onBackClick,
                onActionClick = { onReset(operationType) }
            )

            DifficultyAdjustmentContent(
                level = level,
                operationType = operationType,
                quizRange = quizRange,
                onRangeChanged = onRangeChanged,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            )
        }
    }
}

@Preview(widthDp = 360, heightDp = 640) // 縦画面
@Preview(widthDp = 640, heightDp = 360) // 横画面
@Composable
private fun DifficultyAdjustmentScreenPreview() {
    SansuuKidsTheme {
        DifficultyAdjustmentScreen(
            level = Level.Normal,
            operationType = OperationType.Addition,
            quizRange = QuizRange.Default(OperationType.Addition, Level.Normal),
            onRangeChanged = { _, _, _ -> },
            onReset = {},
            onBackClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}
