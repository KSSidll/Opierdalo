package com.kssidll.opierdalo.data.data

import android.net.Uri
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val imageUri: String?,
    val name: String,
) {
    @Ignore
    constructor(
        imageUri: Uri?,
        name: String
    ): this(
        0,
        imageUri?.toString(),
        name
    )
}