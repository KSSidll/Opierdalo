package com.kssidll.opierdalo.data.repository

import com.kssidll.opierdalo.data.dao.ReminderDao
import com.kssidll.opierdalo.data.data.ReminderEntity
import com.kssidll.opierdalo.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ReminderRepositoryImpl(private val dao: ReminderDao): ReminderRepository {

    // Create

    override suspend fun insert(reminderEntity: ReminderEntity): Long {
        return dao.insert(reminderEntity)
    }

    // Update

    // Delete

    // Read

    override fun get(id: Long): Flow<ReminderEntity?> {
        return dao.get(id)
    }

    override fun all(): Flow<List<ReminderEntity>> {
        return flowOf(
            listOf(
                ReminderEntity("test")
            )
        )
//        return dao.all()
    }
}