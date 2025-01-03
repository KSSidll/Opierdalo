package com.kssidll.opierdalo.domain.usecase.reminder

import com.kssidll.opierdalo.domain.data.Reminder
import com.kssidll.opierdalo.domain.data.toEntity
import com.kssidll.opierdalo.domain.repository.ReminderRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetReminderCompleteStatusUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke(
        reminder: Reminder,
        complete: Boolean,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) = withContext(dispatcher) {
        reminderRepository.update(
            reminder.toEntity().copy(
                complete = complete
            )
        )
    }
}
