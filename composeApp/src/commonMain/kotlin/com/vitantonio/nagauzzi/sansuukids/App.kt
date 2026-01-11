package com.vitantonio.nagauzzi.sansuukids

import androidx.compose.runtime.Composable
import androidx.navigation3.ui.NavDisplay
import com.vitantonio.nagauzzi.sansuukids.navigation.NavigationState
import com.vitantonio.nagauzzi.sansuukids.navigation.key.HomeRoute
import com.vitantonio.nagauzzi.sansuukids.navigation.navigationEntryProvider
import com.vitantonio.nagauzzi.sansuukids.navigation.rememberNavigationState
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    SansuuKidsTheme {
        val navigationState: NavigationState = rememberNavigationState(HomeRoute)

        NavDisplay(
            backStack = navigationState.entries,
            onBack = { navigationState.navigateBack() },
            entryProvider = { key -> navigationEntryProvider(key, navigationState) }
        )
    }
}
