package com.kssidll.opierdalo.domain.usecase.reminder

import com.kssidll.opierdalo.data.data.ReminderEntity
import com.kssidll.opierdalo.domain.repository.ReminderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class InsertReminderEntityUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke(
        reminderEntity: ReminderEntity,
        dispatcher: CoroutineContext = Dispatchers.IO
    ) = withContext(dispatcher) {
        reminderRepository.insert(reminderEntity)
    }
}
