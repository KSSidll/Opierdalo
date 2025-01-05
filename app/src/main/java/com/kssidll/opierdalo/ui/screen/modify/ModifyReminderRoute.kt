package com.kssidll.opierdalo.ui.screen.modify

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ModifyReminderRoute(
    navigateBack: () -> Unit,
    viewModel: ModifyReminderViewModel
) {
    ModifyReminderScreen(
        uiState = viewModel.uiState.collectAsStateWithLifecycle(minActiveState = Lifecycle.State.RESUMED).value,
        onEvent = { event ->
            when (event) {
                is ModifyReminderEvent.NavigateBack -> navigateBack()

                is ModifyReminderEvent.AttemptSubmit -> {
                    if (viewModel.handleEvent(event)) {
                        navigateBack()
                    }
                }

                is ModifyReminderEvent.SetReminderName -> viewModel.handleEvent(event)
            }
        },
    )
}
