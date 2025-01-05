package com.kssidll.opierdalo.ui.screen.modify

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import com.kssidll.opierdalo.data.data.ReminderEntity
import com.kssidll.opierdalo.data.data.ReminderEntityParseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Stable
data class ModifyReminderUiState(
    val id: Long = ReminderEntity.Defaults.ID,
    val name: String = String(),
    val complete: Boolean = ReminderEntity.Defaults.COMPLETE
) {
    fun tryParseReminder(): ReminderEntity? {
        val parseResult = ReminderEntity.tryParseFrom(
            id = id,
            name = name,
            complete = complete
        )

        return when (parseResult) {
            is ReminderEntityParseResult.Success -> parseResult.result
            is ReminderEntityParseResult.Failure -> null
        }
    }

    companion object {
        fun fromReminderEntity(entity: ReminderEntity): ModifyReminderUiState {
            return ModifyReminderUiState(
                id = entity.id,
                name = entity.name,
                complete = entity.complete
            )
        }
    }
}

@Immutable
sealed class ModifyReminderEvent {
    data object NavigateBack: ModifyReminderEvent()
    data object AttemptSubmit: ModifyReminderEvent()
    data class SetReminderName(val name: String): ModifyReminderEvent()
}

abstract class ModifyReminderViewModel(
    @Suppress("PropertyName") protected val _uiState: MutableStateFlow<ModifyReminderUiState> = MutableStateFlow(
        ModifyReminderUiState()
    )
): ViewModel() {
    val uiState = _uiState.asStateFlow()

    fun handleEvent(event: ModifyReminderEvent): Boolean {
        when (event) {
            is ModifyReminderEvent.NavigateBack -> {}

            is ModifyReminderEvent.SetReminderName -> setReminderName(event.name)
            is ModifyReminderEvent.AttemptSubmit -> return attemptSubmit()
        }

        return true
    }

    private fun setReminderName(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                name = name
            )
        }
    }

    private fun attemptSubmit(): Boolean {
        return true
    }
}
