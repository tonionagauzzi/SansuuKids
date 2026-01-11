package com.vitantonio.nagauzzi.sansuukids.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.navigation3.runtime.NavKey
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlinx.serialization.Serializable

class NavigationStateTest {

    @Serializable
    private data object TestRouteA : NavKey

    @Serializable
    private data object TestRouteB : NavKey

    @Test
    fun 初期状態で1つのルートがある() {
        val backStack = mutableStateListOf<NavKey>(TestRouteA)
        val navigationState = NavigationState(backStack)

        assertEquals(1, navigationState.entries.size)
        assertEquals(TestRouteA, navigationState.entries[0])
    }

    @Test
    fun navigateToで新しいルートが追加される() {
        val backStack = mutableStateListOf<NavKey>(TestRouteA)
        val navigationState = NavigationState(backStack)

        navigationState.navigateTo(TestRouteB)

        assertEquals(2, navigationState.entries.size)
        assertEquals(TestRouteA, navigationState.entries[0])
        assertEquals(TestRouteB, navigationState.entries[1])
    }

    @Test
    fun navigateBackで最後のルートが削除される() {
        val backStack = mutableStateListOf<NavKey>(TestRouteA, TestRouteB)
        val navigationState = NavigationState(backStack)

        val removed = navigationState.navigateBack()

        assertEquals(TestRouteB, removed)
        assertEquals(1, navigationState.entries.size)
        assertEquals(TestRouteA, navigationState.entries[0])
    }

    @Test
    fun navigateBackで空のバックスタックの場合はnullを返す() {
        val backStack = mutableStateListOf<NavKey>()
        val navigationState = NavigationState(backStack)

        val removed = navigationState.navigateBack()

        assertNull(removed)
    }

    @Test
    fun 複数回のnavigateToで正しい順序でスタックが積まれる() {
        val backStack = mutableStateListOf<NavKey>(TestRouteA)
        val navigationState = NavigationState(backStack)

        navigationState.navigateTo(TestRouteB)
        navigationState.navigateTo(TestRouteA)

        assertEquals(3, navigationState.entries.size)
        assertEquals(listOf(TestRouteA, TestRouteB, TestRouteA), navigationState.entries)
    }

    @Test
    fun 複数回のnavigateBackで正しい順序で削除される() {
        val backStack = mutableStateListOf<NavKey>(TestRouteA, TestRouteB)
        val navigationState = NavigationState(backStack)

        assertEquals(TestRouteB, navigationState.navigateBack())
        assertEquals(TestRouteA, navigationState.navigateBack())
        assertNull(navigationState.navigateBack())
    }
}
