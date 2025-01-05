package com.kssidll.opierdalo.ui.screen.modify.edit

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.kssidll.opierdalo.ui.screen.modify.ModifyReminderRoute

@SuppressLint("ComposeViewModelForwarding")
@Composable
fun EditReminderRoute(
    navigateBack: () -> Unit,
    viewModel: EditReminderViewModel = hiltViewModel()
) {
    ModifyReminderRoute(
        navigateBack = navigateBack,
        viewModel = viewModel
    )
}
