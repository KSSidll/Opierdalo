package com.kssidll.opierdalo.domain.data

import com.kssidll.opierdalo.data.data.ReminderEntity

data class Reminder(
    val entity: ReminderEntity
) {
    val name get() = entity.name
    val complete get() = entity.complete
}

fun Reminder.toEntity() = this.entity
fun Reminder.identifier() = this.toEntity().id

fun ReminderEntity.toReminder() = Reminder(
    entity = this
)