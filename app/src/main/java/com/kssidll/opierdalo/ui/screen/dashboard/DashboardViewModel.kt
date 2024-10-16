package com.kssidll.opierdalo.ui.screen.dashboard


import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kssidll.opierdalo.domain.data.Reminder
import com.kssidll.opierdalo.domain.usecase.reminder.GetAllReminderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
data class DashboardUiState(
    val reminder: ImmutableList<Reminder> = persistentListOf()
)

@Immutable
sealed class DashboardEvent {
    data object NavigateSettings: DashboardEvent()
    data object NavigateAddNewReminder: DashboardEvent()
}

@HiltViewModel
class DashboardViewModel @Inject constructor(
    getAllReminderUseCase: GetAllReminderUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(
        DashboardUiState()
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAllReminderUseCase().collect {
                _uiState.update { currentState ->
                    currentState.copy(
                        reminder = it.toImmutableList()
                    )
                }
            }
        }
    }

    fun handleEvent(event: DashboardEvent) {
        when (event) {
            is DashboardEvent.NavigateSettings -> {}

            is DashboardEvent.NavigateAddNewReminder -> {}
        }
    }
}
