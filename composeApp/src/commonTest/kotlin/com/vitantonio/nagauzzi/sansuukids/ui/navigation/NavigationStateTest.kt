package com.vitantonio.nagauzzi.sansuukids.ui.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.SaverScope
import com.vitantonio.nagauzzi.sansuukids.model.Level
import com.vitantonio.nagauzzi.sansuukids.model.OperationType
import com.vitantonio.nagauzzi.sansuukids.model.QuizRange
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.HomeRoute as TestRouteA
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.OperationTypeSelectionRoute as TestRouteB
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.QuizRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.SansuuKidsRoute
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class NavigationStateTest {

    @Test
    fun 初期状態で1つのルートがある() {
        // Given: 1つのルートを持つバックスタックを作成する
        val backStack = mutableStateListOf<SansuuKidsRoute>(TestRouteA)
        val navigationState = NavigationState(backStack)

        // When: NavigationStateを初期化する（アクションなし）

        // Then: エントリが1つ存在し、それがTestRouteAである
        assertEquals(1, navigationState.entries.size)
        assertEquals(TestRouteA, navigationState.entries[0])
    }

    @Test
    fun navigateToで新しいルートが追加される() {
        // Given: 1つのルートを持つNavigationStateを初期化する
        val backStack = mutableStateListOf<SansuuKidsRoute>(TestRouteA)
        val navigationState = NavigationState(backStack)

        // When: navigateToで新しいルートに遷移する
        navigationState.navigateTo(TestRouteB)

        // Then: バックスタックに2つのルートが順番に追加される
        assertEquals(2, navigationState.entries.size)
        assertEquals(TestRouteA, navigationState.entries[0])
        assertEquals(TestRouteB, navigationState.entries[1])
    }

    @Test
    fun navigateBackで最後のルートが削除される() {
        // Given: 2つのルートを持つNavigationStateを初期化する
        val backStack = mutableStateListOf(TestRouteA, TestRouteB)
        val navigationState = NavigationState(backStack)

        // When: navigateBackで戻るナビゲーションを実行する
        val removed = navigationState.navigateBack()

        // Then: 最後のルートが削除され、削除されたルートが返される
        assertEquals(TestRouteB, removed)
        assertEquals(1, navigationState.entries.size)
        assertEquals(TestRouteA, navigationState.entries[0])
    }

    @Test
    fun navigateBackで空のバックスタックの場合はnullを返す() {
        // Given: 空のバックスタックを持つNavigationStateを初期化する
        val backStack = mutableStateListOf<SansuuKidsRoute>()
        val navigationState = NavigationState(backStack)

        // When: navigateBackで戻るナビゲーションを実行する
        val removed = navigationState.navigateBack()

        // Then: nullが返される
        assertNull(removed)
    }

    @Test
    fun 複数回のnavigateToで正しい順序でスタックが積まれる() {
        // Given: 1つのルートを持つNavigationStateを初期化する
        val backStack = mutableStateListOf<SansuuKidsRoute>(TestRouteA)
        val navigationState = NavigationState(backStack)

        // When: navigateToを複数回実行する
        navigationState.navigateTo(TestRouteB)
        navigationState.navigateTo(TestRouteA)

        // Then: ルートが順番にスタックに積まれる
        assertEquals(3, navigationState.entries.size)
        assertEquals(listOf(TestRouteA, TestRouteB, TestRouteA), navigationState.entries)
    }

    @Test
    fun 複数回のnavigateBackで正しい順序で削除される() {
        // Given: 2つのルートを持つNavigationStateを初期化する
        val backStack = mutableStateListOf(TestRouteA, TestRouteB)
        val navigationState = NavigationState(backStack)

        // When: navigateBackを複数回実行する
        // Then: ルートが後ろから順番に削除され、空になったらnullが返される
        assertEquals(TestRouteB, navigationState.navigateBack())
        assertEquals(TestRouteA, navigationState.navigateBack())
        assertNull(navigationState.navigateBack())
    }

    @Test
    fun popToHomeで最初のルートだけが残る() {
        // Given: 3つのルートを持つNavigationStateを初期化する
        val backStack = mutableStateListOf(TestRouteA, TestRouteB, TestRouteA)
        val navigationState = NavigationState(backStack)

        // When: popToHomeを実行する
        navigationState.popToHome()

        // Then: 最初のルートだけが残る
        assertEquals(1, navigationState.entries.size)
        assertEquals(TestRouteA, navigationState.entries[0])
    }

    @Test
    fun popToHomeで1つのルートしかない場合は何も削除しない() {
        // Given: 1つのルートを持つNavigationStateを初期化する
        val backStack = mutableStateListOf<SansuuKidsRoute>(TestRouteA)
        val navigationState = NavigationState(backStack)

        // When: popToHomeを実行する
        navigationState.popToHome()

        // Then: ルートは変わらない
        assertEquals(1, navigationState.entries.size)
        assertEquals(TestRouteA, navigationState.entries[0])
    }

    @Test
    fun NavigationStateを保存して復元できる() {
        // Given: 複数のルートを持つNavigationStateを作成する
        val operationType = OperationType.Addition
        val level = Level.Easy
        val quizRange = QuizRange.Default(operationType, level)
        val backStack = mutableStateListOf(
            TestRouteA,
            TestRouteB,
            QuizRoute(quizRange)
        )
        val originalState = NavigationState(backStack)
        val saver = NavigationState.saver()

        // When: 保存して復元する
        val saved = with(saver) { SaverScope { true }.save(originalState) }
        assertNotNull(saved)
        val restoredState = saver.restore(saved)

        // Then: 復元されたStateが元のStateと同じエントリを持つ
        assertNotNull(restoredState)
        assertEquals(3, restoredState.entries.size)
        assertEquals(TestRouteA, restoredState.entries[0])
        assertEquals(TestRouteB, restoredState.entries[1])
        val quizRoute = restoredState.entries[2] as QuizRoute
        assertEquals(OperationType.Addition, quizRoute.quizRange.operationType)
        assertEquals(Level.Easy, quizRoute.quizRange.level)
    }

    @Test
    fun 空のバックスタックを保存して復元できる() {
        // Given: 空のバックスタックを持つNavigationStateを作成する
        val backStack = mutableStateListOf<SansuuKidsRoute>()
        val originalState = NavigationState(backStack)
        val saver = NavigationState.saver()

        // When: 保存して復元する
        val saved = with(saver) { SaverScope { true }.save(originalState) }
        assertNotNull(saved)
        val restoredState = saver.restore(saved)

        // Then: 復元されたStateが空のエントリを持つ
        assertNotNull(restoredState)
        assertEquals(0, restoredState.entries.size)
    }
}
