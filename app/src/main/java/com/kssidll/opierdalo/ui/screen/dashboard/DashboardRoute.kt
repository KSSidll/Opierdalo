package com.kssidll.opierdalo.ui.screen.dashboard


import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.kssidll.opierdalo.domain.notification.ReminderNotificationScheduler
import com.kssidll.opierdalo.helper.checkPermission

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DashboardRoute(
    navigateSettings: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    @SuppressLint("NewApi") // Handled on launch level
    val notificationPreparationLauncher =
        ReminderNotificationScheduler.rememberPreparationLauncher(
            onSuccess = {
                if (checkPermission(context, Manifest.permission.POST_NOTIFICATIONS)) {
                    ReminderNotificationScheduler(context).start()
                }
            },
            onFailure = {

            }
        )

    DashboardScreen(
        uiState = viewModel.uiState.collectAsStateWithLifecycle(minActiveState = Lifecycle.State.RESUMED).value,
        onEvent = { event ->
            when (event) {
                is DashboardEvent.NavigateSettings -> navigateSettings()

                is DashboardEvent.NavigateAddNewReminder -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        notificationPreparationLauncher.launchPermissionRequest()
                    } else {
                        if (checkPermission(context, Manifest.permission.POST_NOTIFICATIONS)) {
                            ReminderNotificationScheduler(context).start()
                        }
                    }
                }

                is DashboardEvent.SetNewReminderName -> viewModel.handleEvent(event)
            }
        },
    )
}
