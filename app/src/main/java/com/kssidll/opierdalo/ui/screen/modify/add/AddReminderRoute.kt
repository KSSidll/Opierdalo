package com.kssidll.opierdalo.ui.screen.modify.add

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.kssidll.opierdalo.ui.screen.modify.ModifyReminderRoute

@SuppressLint("ComposeViewModelForwarding")
@Composable
fun AddReminderRoute(
    navigateBack: () -> Unit,
    viewModel: AddReminderViewModel = hiltViewModel()
) {
    ModifyReminderRoute(
        navigateBack = navigateBack,
        viewModel = viewModel
    )
}
