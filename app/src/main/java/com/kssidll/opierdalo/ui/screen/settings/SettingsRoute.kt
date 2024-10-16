package com.kssidll.opierdalo.ui.screen.settings


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SettingsRoute(
    navigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    SettingsScreen(
        uiState = viewModel.uiState.collectAsStateWithLifecycle(minActiveState = Lifecycle.State.RESUMED).value,
        onEvent = { event ->
            when (event) {
                is SettingsEvent.NavigateBack -> navigateBack()
                is SettingsEvent.SetLocale -> viewModel.handleEvent(event)
                is SettingsEvent.SetColorScheme -> viewModel.handleEvent(event)
                is SettingsEvent.SetDynamicColor -> viewModel.handleEvent(event)
            }
        },
    )
}
