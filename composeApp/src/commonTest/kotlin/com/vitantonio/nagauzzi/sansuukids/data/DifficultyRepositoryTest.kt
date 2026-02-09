package com.vitantonio.nagauzzi.sansuukids.data

import com.vitantonio.nagauzzi.sansuukids.logic.GenerateQuiz
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.Mode
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DifficultyRepositoryTest {

    @Test
    fun カスタム未設定の場合はデフォルト範囲が返される() = runTest {
        // Given: 空のリポジトリ
        val repository = DifficultyRepositoryImpl(FakePreferencesDataStore())

        // When: カスタム範囲を取得する
        val ranges = repository.getCustomRanges(Mode.Addition, Level.Easy).first()

        // Then: デフォルト範囲が返される
        val range = ranges.first { it.operationType == OperationType.Addition }
        val defaultRange = QuizRange.Default(OperationType.Addition, Level.Easy)
        assertEquals(defaultRange.min, range.min)
        assertEquals(defaultRange.max, range.max)
    }

    @Test
    fun カスタム範囲を設定すると反映される() = runTest {
        // Given: 空のリポジトリ
        val repository = DifficultyRepositoryImpl(FakePreferencesDataStore())

        // When: カスタム範囲を設定する
        repository.setCustomRange(OperationType.Addition, Level.Easy, min = 1, max = 10)

        // Then: 設定した範囲が返される
        val ranges = repository.getCustomRanges(Mode.Addition, Level.Easy).first()
        val range = ranges.first { it.operationType == OperationType.Addition }
        assertEquals(1, range.min)
        assertEquals(10, range.max)
    }

    @Test
    fun リセットするとデフォルト範囲に戻る() = runTest {
        // Given: カスタム範囲が設定されたリポジトリ
        val repository = DifficultyRepositoryImpl(FakePreferencesDataStore())
        repository.setCustomRange(OperationType.Subtraction, Level.Normal, min = 5, max = 50)

        // When: リセットする
        repository.resetToDefault(OperationType.Subtraction, Level.Normal)

        // Then: デフォルト範囲に戻る
        val ranges = repository.getCustomRanges(Mode.Subtraction, Level.Normal).first()
        val range = ranges.first { it.operationType == OperationType.Subtraction }
        val defaultRange = QuizRange.Default(OperationType.Subtraction, Level.Normal)
        assertEquals(defaultRange.min, range.min)
        assertEquals(defaultRange.max, range.max)
    }

    @Test
    fun 異なる演算タイプの設定は独立している() = runTest {
        // Given: 空のリポジトリ
        val repository = DifficultyRepositoryImpl(FakePreferencesDataStore())

        // When: 足し算のカスタム範囲を設定する
        repository.setCustomRange(OperationType.Addition, Level.Easy, min = 1, max = 15)

        // Then: 引き算のカスタム範囲はデフォルトのまま
        val subRanges = repository.getCustomRanges(Mode.Subtraction, Level.Easy).first()
        val subRange = subRanges.first { it.operationType == OperationType.Subtraction }
        val defaultRange = QuizRange.Default(OperationType.Subtraction, Level.Easy)
        assertEquals(defaultRange.min, subRange.min)
        assertEquals(defaultRange.max, subRange.max)
    }

    @Test
    fun すべてモードで全演算タイプの範囲が返される() = runTest {
        // Given: 空のリポジトリ
        val repository = DifficultyRepositoryImpl(FakePreferencesDataStore())

        // When: すべてモードのカスタム範囲を取得する
        val ranges = repository.getCustomRanges(Mode.All, Level.Easy).first()

        // Then: 4つの演算タイプ分の範囲が返される
        assertEquals(4, ranges.size)
        assertEquals(OperationType.Addition, ranges[0].operationType)
        assertEquals(OperationType.Subtraction, ranges[1].operationType)
        assertEquals(OperationType.Multiplication, ranges[2].operationType)
        assertEquals(OperationType.Division, ranges[3].operationType)
    }

    @Test
    fun わり算モードではわり算の範囲が返される() = runTest {
        // Given: わり算のカスタム範囲を設定する
        val repository = DifficultyRepositoryImpl(FakePreferencesDataStore())
        repository.setCustomRange(OperationType.Division, Level.Easy, min = 2, max = 15)

        // When: わり算モードのカスタム範囲を取得する
        val ranges = repository.getCustomRanges(Mode.Division, Level.Easy).first()

        // Then: わり算の設定値が返される
        val range = ranges.first { it.operationType == OperationType.Division }
        assertEquals(2, range.min)
        assertEquals(15, range.max)
    }
}
