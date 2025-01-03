package com.kssidll.opierdalo.domain.repository

import com.kssidll.opierdalo.data.data.ReminderEntity
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {

    // Create

    suspend fun insert(reminderEntity: ReminderEntity): Long

    // Update

    suspend fun update(reminderEntity: ReminderEntity)

    // Delete

    // Read

    fun get(id: Long): Flow<ReminderEntity?>

    fun all(): Flow<List<ReminderEntity>>
}