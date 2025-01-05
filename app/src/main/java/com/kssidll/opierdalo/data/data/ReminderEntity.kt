package com.kssidll.opierdalo.data.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.kssidll.opierdalo.data.data.ReminderEntityParseError.BlankNameError
import com.kssidll.opierdalo.data.data.ReminderEntityParseResult.Failure
import com.kssidll.opierdalo.data.data.ReminderEntityParseResult.Success
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

/**
 * Possible [ReminderEntity] parse errors
 *
 * [BlankNameError] if [ReminderEntity.name] was Blank
 */
sealed class ReminderEntityParseError {
    /**
     * [ReminderEntity.name] was Blank
     */
    data object BlankNameError: ReminderEntityParseError()
}

/**
 * Reminder entity parse result
 *
 * either a [Success] or [Failure]
 * [Success] provides [ReminderEntity] as result
 * [Failure] provides list of [ReminderEntityParseError] as errors
 */
sealed class ReminderEntityParseResult {
    /**
     * [Success] parse result
     *
     * @property result parsed [ReminderEntity]
     */
    data class Success(val result: ReminderEntity): ReminderEntityParseResult()

    /**
     * [Failure] parse result
     *
     * @property errors list of [ReminderEntityParseError]
     */
    data class Failure(val errors: ImmutableList<ReminderEntityParseError>): ReminderEntityParseResult()
}

@Entity
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = Defaults.ID,
    val name: String,
    val complete: Boolean = Defaults.COMPLETE,
) {
    object Defaults {
        @Ignore
        const val ID = 0L

        @Ignore
        const val COMPLETE = false
    }

    companion object {
        /**
         * Checks if provided [name] is valid
         *
         * @param name name to check
         * @return null if [name] is valid, appropriate [ReminderEntityParseError] otherwise
         */
        @Ignore
        fun checkParseFromName(name: String): ReminderEntityParseError? {
            return if (name.isBlank()) ReminderEntityParseError.BlankNameError
            else null
        }

        /**
         * Attempts to parse [ReminderEntity] from provided data
         *
         * @return appropriate [ReminderEntityParseResult] with result data
         */
        @Ignore
        fun tryParseFrom(
            id: Long,
            name: String,
            complete: Boolean
        ): ReminderEntityParseResult {
            val errors = mutableListOf<ReminderEntityParseError>()

            checkParseFromName(name)?.let {
                errors.add(it)
            }

            return if (errors.isNotEmpty()) {
                ReminderEntityParseResult.Failure(errors.toImmutableList())
            } else {
                ReminderEntityParseResult.Success(
                    ReminderEntity(
                        id = id,
                        name = name,
                        complete = complete
                    )
                )
            }
        }
    }
}