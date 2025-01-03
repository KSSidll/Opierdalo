package com.kssidll.opierdalo.ui.screen.dashboard


import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kssidll.opierdalo.data.data.ReminderEntity
import com.kssidll.opierdalo.domain.data.Reminder
import com.kssidll.opierdalo.domain.usecase.reminder.GetAllReminderUseCase
import com.kssidll.opierdalo.domain.usecase.reminder.InsertReminderEntityUseCase
import com.kssidll.opierdalo.domain.usecase.reminder.SetReminderCompleteStatusUseCase
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
    val reminder: ImmutableList<Reminder> = persistentListOf(),
    val newReminderName: String = String()
)

@Immutable
sealed class DashboardEvent {
    data object NavigateSettings: DashboardEvent()
    data object NavigateAddNewReminder: DashboardEvent()
    data class SetReminderCompleteStatus(val reminder: Reminder, val complete: Boolean): DashboardEvent()
}

@HiltViewModel
class DashboardViewModel @Inject constructor(
    getAllReminderUseCase: GetAllReminderUseCase,
    private val setReminderCompleteStatusUseCase: SetReminderCompleteStatusUseCase,
    private val insertReminderEntityUseCase: InsertReminderEntityUseCase
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
                        reminder = it.sortedBy { it.complete }.toImmutableList()
                    )
                }
            }
        }
    }

    fun handleEvent(event: DashboardEvent) {
        when (event) {
            is DashboardEvent.NavigateSettings -> {}

            is DashboardEvent.NavigateAddNewReminder -> {
                //TODO remove and nav in route
                viewModelScope.launch {
                    insertReminderEntityUseCase(
                        ReminderEntity(
                            name = "test"
                        )
                    )
                }
            }

            is DashboardEvent.SetReminderCompleteStatus -> setReminderCompleteStatus(event.reminder, event.complete)
        }
    }

    private fun setReminderCompleteStatus(reminder: Reminder, complete: Boolean) = viewModelScope.launch {
        setReminderCompleteStatusUseCase(reminder, complete)
    }
}
