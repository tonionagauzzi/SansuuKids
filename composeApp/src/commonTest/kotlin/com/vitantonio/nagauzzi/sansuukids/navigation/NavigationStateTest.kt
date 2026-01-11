package com.vitantonio.nagauzzi.sansuukids.navigation

import androidx.compose.runtime.mutableStateListOf
import com.vitantonio.nagauzzi.sansuukids.navigation.key.HomeRoute as TestRouteA
import com.vitantonio.nagauzzi.sansuukids.navigation.key.ModeSelectionRoute as TestRouteB
import com.vitantonio.nagauzzi.sansuukids.navigation.key.SansuuKidsRoute
import kotlin.test.Test
import kotlin.test.assertEquals
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
}
