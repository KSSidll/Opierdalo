package com.kssidll.opierdalo.domain.data

import com.kssidll.opierdalo.data.data.ReminderEntity

data class Reminder(
    val entity: ReminderEntity,
    val name: String,
    val complete: Boolean
)

fun Reminder.toEntity() = this.entity
fun Reminder.identifier() = this.toEntity().id

fun ReminderEntity.toReminder() = Reminder(
    entity = this,
    name = name,
    complete = complete
)