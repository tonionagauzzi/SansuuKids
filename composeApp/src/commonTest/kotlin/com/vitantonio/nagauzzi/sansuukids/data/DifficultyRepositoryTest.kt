package com.vitantonio.nagauzzi.sansuukids.data

import com.vitantonio.nagauzzi.sansuukids.model.Level
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
        val range = repository.getCustomRange(OperationType.Addition, Level.Easy).first()

        // Then: デフォルト範囲が返される
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
        val range = repository.getCustomRange(OperationType.Addition, Level.Easy).first()
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
        val range = repository.getCustomRange(OperationType.Subtraction, Level.Normal).first()
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
        val subRange = repository.getCustomRange(OperationType.Subtraction, Level.Easy).first()
        val defaultRange = QuizRange.Default(OperationType.Subtraction, Level.Easy)
        assertEquals(defaultRange.min, subRange.min)
        assertEquals(defaultRange.max, subRange.max)
    }

    @Test
    fun すべてモードのデフォルト範囲が正しく返される() = runTest {
        // Given: 空のリポジトリ
        val repository = DifficultyRepositoryImpl(FakePreferencesDataStore())

        // When: すべてモードのカスタム範囲を取得する
        val range = repository.getCustomRange(OperationType.All, Level.Easy).first()

        // Then: すべてモードのデフォルト範囲が返される
        val defaultRange = QuizRange.Default(OperationType.All, Level.Easy)
        assertEquals(defaultRange.min, range.min)
        assertEquals(defaultRange.max, range.max)
    }

    @Test
    fun わり算のカスタム範囲が正しく返される() = runTest {
        // Given: わり算のカスタム範囲を設定する
        val repository = DifficultyRepositoryImpl(FakePreferencesDataStore())
        repository.setCustomRange(OperationType.Division, Level.Easy, min = 2, max = 15)

        // When: わり算のカスタム範囲を取得する
        val range = repository.getCustomRange(OperationType.Division, Level.Easy).first()

        // Then: わり算の設定値が返される
        assertEquals(2, range.min)
        assertEquals(15, range.max)
    }
}
