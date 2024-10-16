package com.kssidll.opierdalo.di.module

import android.content.Context
import com.kssidll.opierdalo.data.dao.ReminderDao
import com.kssidll.opierdalo.data.database.AppDatabase
import com.kssidll.opierdalo.data.repository.ReminderRepositoryImpl
import com.kssidll.opierdalo.domain.repository.ReminderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext appContext: Context,
    ): AppDatabase {
        return AppDatabase.buildExternal(appContext)
    }

    @Provides
    fun provideReminderDao(appDatabase: AppDatabase): ReminderDao {
        return appDatabase.reminderDao()
    }

    @Provides
    fun provideReminderRepository(reminderDao: ReminderDao): ReminderRepository {
        return ReminderRepositoryImpl(reminderDao)
    }
}
