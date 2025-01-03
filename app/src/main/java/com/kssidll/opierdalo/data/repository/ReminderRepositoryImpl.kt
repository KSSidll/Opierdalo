package com.kssidll.opierdalo.data.repository

import com.kssidll.opierdalo.data.dao.ReminderDao
import com.kssidll.opierdalo.data.data.ReminderEntity
import com.kssidll.opierdalo.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow

class ReminderRepositoryImpl(private val dao: ReminderDao): ReminderRepository {

    // Create

    override suspend fun insert(reminderEntity: ReminderEntity): Long {
        return dao.insert(reminderEntity)
    }

    // Update

    override suspend fun update(reminderEntity: ReminderEntity) {
        dao.update(reminderEntity)
    }

    // Delete

    // Read

    override fun get(id: Long): Flow<ReminderEntity?> {
        return dao.get(id)
    }

    override fun all(): Flow<List<ReminderEntity>> {
        return dao.all()
    }
}