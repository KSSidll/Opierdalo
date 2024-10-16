package com.kssidll.opierdalo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kssidll.opierdalo.data.data.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    // Create

    @Insert
    suspend fun insert(reminderEntity: ReminderEntity): Long

    // Update

    @Update
    suspend fun update(reminderEntity: ReminderEntity)

    // Delete

    @Delete
    suspend fun delete(reminderEntity: ReminderEntity)

    // Read

    @Query("SELECT ReminderEntity.* FROM ReminderEntity WHERE ReminderEntity.id = :id")
    fun get(id: Long): Flow<ReminderEntity?>

    @Query("SELECT ReminderEntity.* FROM ReminderEntity")
    fun all(): Flow<List<ReminderEntity>>
}