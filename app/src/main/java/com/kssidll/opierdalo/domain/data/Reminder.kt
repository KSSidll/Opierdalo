package com.kssidll.opierdalo.domain.data

import android.net.Uri
import com.kssidll.opierdalo.data.data.ReminderEntity

data class Reminder(
    val entity: ReminderEntity,
    val imageUri: Uri?,
    val name: String
)

fun Reminder.toEntity() = this.entity

fun ReminderEntity.toReminder() = Reminder(
    entity = this,
    imageUri = imageUri?.let { Uri.parse(it) },
    name = name
)