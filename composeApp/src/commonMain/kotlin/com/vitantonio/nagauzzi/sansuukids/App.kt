package com.vitantonio.nagauzzi.sansuukids

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.vitantonio.nagauzzi.sansuukids.data.MedalRepositoryProvider
import com.vitantonio.nagauzzi.sansuukids.data.SettingRepositoryProvider
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.key.HomeRoute
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.navigationEntryProvider
import com.vitantonio.nagauzzi.sansuukids.ui.navigation.rememberNavigationState
import com.vitantonio.nagauzzi.sansuukids.ui.theme.SansuuKidsTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    SansuuKidsTheme {
        val navigationState = rememberNavigationState(HomeRoute)

        NavDisplay(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .safeContentPadding(),
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            backStack = navigationState.entries,
            onBack = { navigationState.navigateBack() },
            entryProvider = { key ->
                navigationEntryProvider(
                    key = key,
                    navigationState = navigationState,
                    medalRepository = MedalRepositoryProvider.medalRepository,
                    settingRepository = SettingRepositoryProvider.settingRepository
                )
            }
        )
    }
}
