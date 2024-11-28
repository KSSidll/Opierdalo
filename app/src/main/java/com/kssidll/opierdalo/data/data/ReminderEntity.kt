package com.kssidll.opierdalo.data.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
) {
    @Ignore
    constructor(
        name: String
    ): this(
        0,
        name
    )
}