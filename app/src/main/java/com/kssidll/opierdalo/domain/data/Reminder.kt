package com.kssidll.opierdalo.domain.data

import com.kssidll.opierdalo.data.data.ReminderEntity

data class Reminder(
    val entity: ReminderEntity,
    val name: String
)

fun Reminder.toEntity() = this.entity

fun ReminderEntity.toReminder() = Reminder(
    entity = this,
    name = name
)