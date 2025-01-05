package com.kssidll.opierdalo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kssidll.opierdalo.APPLICATION_NAME
import com.kssidll.opierdalo.data.dao.ReminderDao
import com.kssidll.opierdalo.data.data.ReminderEntity
import java.io.File

/**
 * default database name
 */
const val DATABASE_NAME: String = APPLICATION_NAME + "_database.db"

/**
 * @return absolute path to external database file as [File]
 */
fun Context.externalDbFile(): File =
    File(getExternalFilesDir(null)!!.absolutePath.plus("/database/$DATABASE_NAME"))

@Database(
    version = 1,
    entities = [
        ReminderEntity::class,
    ]
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun reminderDao(): ReminderDao

    companion object {
        /**
         * @param context app context
         * @param name name of the database or absolute path if not internal location, Defaults to internal location with [DATABASE_NAME] name
         * @return [RoomDatabase.Builder] of [AppDatabase] for [name]
         */
        private fun builder(
            context: Context,
            name: String = DATABASE_NAME
        ): Builder<AppDatabase> {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                name
            )
        }

        /**
         * @param context app context
         * @return [AppDatabase] created in external [Context.externalDbFile] location, doesn't ensure database file creation
         */
        fun buildExternal(context: Context): AppDatabase {
            return builder(
                context,
                context.externalDbFile().absolutePath
            ).build()
        }
    }
}