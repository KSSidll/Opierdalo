package com.kssidll.opierdalo.di.module

import com.kssidll.opierdalo.domain.repository.ReminderRepository
import com.kssidll.opierdalo.domain.usecase.reminder.GetAllReminderEntityUseCase
import com.kssidll.opierdalo.domain.usecase.reminder.GetAllReminderUseCase
import com.kssidll.opierdalo.domain.usecase.reminder.GetReminderEntityUseCase
import com.kssidll.opierdalo.domain.usecase.reminder.GetReminderUseCase
import com.kssidll.opierdalo.domain.usecase.reminder.InsertReminderEntityUseCase
import com.kssidll.opierdalo.domain.usecase.reminder.SetReminderCompleteStatusUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ReminderUseCaseModule {
    @Provides
    fun provideInsertReminderEntityUseCase(
        reminderRepository: ReminderRepository
    ): InsertReminderEntityUseCase {
        return InsertReminderEntityUseCase(reminderRepository)
    }

    @Provides
    fun provideGetReminderEntityUseCase(
        reminderRepository: ReminderRepository
    ): GetReminderEntityUseCase {
        return GetReminderEntityUseCase(reminderRepository)
    }

    @Provides
    fun provideGetReminderUseCase(
        getReminderEntityUseCase: GetReminderEntityUseCase
    ): GetReminderUseCase {
        return GetReminderUseCase(getReminderEntityUseCase)
    }

    @Provides
    fun provideGetAllReminderEntityUseCase(
        reminderRepository: ReminderRepository
    ): GetAllReminderEntityUseCase {
        return GetAllReminderEntityUseCase(reminderRepository)
    }

    @Provides
    fun provideGetAllReminderUseCase(
        getAllReminderEntityUseCase: GetAllReminderEntityUseCase
    ): GetAllReminderUseCase {
        return GetAllReminderUseCase(getAllReminderEntityUseCase)
    }

    @Provides
    fun provideSetReminderCompleteStatusUseCase(
        reminderRepository: ReminderRepository
    ): SetReminderCompleteStatusUseCase {
        return SetReminderCompleteStatusUseCase(reminderRepository)
    }
}