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

class GetReminderEntityUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
    operator fun invoke(
        id: Long,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Flow<ReminderEntity?> {
        return reminderRepository.get(id)
            .distinctUntilChanged()
            .cancellable()
            .flowOn(dispatcher)
    }
}

class GetReminderUseCase @Inject constructor(
    private val getReminderEntityUseCase: GetReminderEntityUseCase
) {
    operator fun invoke(
        id: Long,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Flow<Reminder?> {
        return getReminderEntityUseCase(id, dispatcher)
            .map { it?.toReminder() }
    }
}