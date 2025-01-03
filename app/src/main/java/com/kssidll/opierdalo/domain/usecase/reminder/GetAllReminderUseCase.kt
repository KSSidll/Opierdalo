package com.kssidll.opierdalo.domain.usecase.reminder

import com.kssidll.opierdalo.data.data.ReminderEntity
import com.kssidll.opierdalo.domain.data.Reminder
import com.kssidll.opierdalo.domain.data.toReminder
import com.kssidll.opierdalo.domain.repository.ReminderRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllReminderEntityUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
    operator fun invoke(
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Flow<List<ReminderEntity>> {
        return reminderRepository.all()
            .distinctUntilChanged()
            .cancellable()
            .flowOn(dispatcher)
    }
}

class GetAllReminderUseCase @Inject constructor(
    private val getAllReminderEntityUseCase: GetAllReminderEntityUseCase
) {
    operator fun invoke(
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Flow<List<Reminder>> {
        return getAllReminderEntityUseCase(dispatcher)
            .map { entityList -> entityList.map { it.toReminder() } }
    }
}
