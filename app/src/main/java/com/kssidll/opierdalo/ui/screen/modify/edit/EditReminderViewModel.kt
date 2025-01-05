package com.kssidll.opierdalo.ui.screen.modify.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.kssidll.opierdalo.NavigationDestinations
import com.kssidll.opierdalo.domain.usecase.reminder.GetReminderEntityUseCase
import com.kssidll.opierdalo.ui.screen.modify.ModifyReminderUiState
import com.kssidll.opierdalo.ui.screen.modify.ModifyReminderViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditReminderViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getReminderEntityUseCase: GetReminderEntityUseCase
): ModifyReminderViewModel() {
    init {
        val reminderId = savedStateHandle.toRoute<NavigationDestinations.EditReminder>().reminderId

        viewModelScope.launch {
            getReminderEntityUseCase(reminderId).first()?.let {
                _uiState.update { _ ->
                    ModifyReminderUiState.fromReminderEntity(it)
                }
            }
        }
    }
}
