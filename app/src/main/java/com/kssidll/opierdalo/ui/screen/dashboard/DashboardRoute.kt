package com.kssidll.opierdalo.ui.screen.dashboard


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kssidll.opierdalo.domain.data.toEntity

@Composable
fun DashboardRoute(
    navigateSettings: () -> Unit,
    navigateAddReminder: () -> Unit,
    navigateEditReminder: (reminderId: Long) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    DashboardScreen(
        uiState = viewModel.uiState.collectAsStateWithLifecycle(minActiveState = Lifecycle.State.RESUMED).value,
        onEvent = { event ->
            when (event) {
                is DashboardEvent.NavigateSettings -> navigateSettings()
                is DashboardEvent.NavigateAddNewReminder -> navigateAddReminder()
                is DashboardEvent.NavigateEditReminder -> navigateEditReminder(event.reminder.toEntity().id)
                is DashboardEvent.ToggleReminderCompleteStatus -> viewModel.handleEvent(event)
            }
        },
    )
}
