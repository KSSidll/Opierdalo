package com.kssidll.opierdalo.ui.screen.dashboard


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DashboardRoute(
    navigateSettings: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    DashboardScreen(
        uiState = viewModel.uiState.collectAsStateWithLifecycle(minActiveState = Lifecycle.State.RESUMED).value,
        onEvent = { event ->
            when (event) {
                is DashboardEvent.NavigateSettings -> navigateSettings()
                is DashboardEvent.NavigateAddNewReminder -> viewModel.handleEvent(event)
                is DashboardEvent.SetReminderCompleteStatus -> viewModel.handleEvent(event)
            }
        },
    )
}
