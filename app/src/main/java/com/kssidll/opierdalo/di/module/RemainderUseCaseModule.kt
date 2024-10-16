package com.kssidll.opierdalo.di.module

import com.kssidll.opierdalo.domain.repository.ReminderRepository
import com.kssidll.opierdalo.domain.usecase.reminder.GetAllReminderEntityUseCase
import com.kssidll.opierdalo.domain.usecase.reminder.GetAllReminderUseCase
import com.kssidll.opierdalo.domain.usecase.reminder.GetReminderEntityUseCase
import com.kssidll.opierdalo.domain.usecase.reminder.GetReminderUseCase
import com.kssidll.opierdalo.domain.usecase.reminder.InsertReminderEntityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ReminderUseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideInsertReminderEntityUseCase(
        reminderRepository: ReminderRepository
    ): InsertReminderEntityUseCase {
        return InsertReminderEntityUseCase(reminderRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetReminderEntityUseCase(
        reminderRepository: ReminderRepository
    ): GetReminderEntityUseCase {
        return GetReminderEntityUseCase(reminderRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetReminderUseCase(
        getReminderEntityUseCase: GetReminderEntityUseCase
    ): GetReminderUseCase {
        return GetReminderUseCase(getReminderEntityUseCase)
    }

    @Provides
    @ViewModelScoped
    fun provideGetAllReminderEntityUseCase(
        reminderRepository: ReminderRepository
    ): GetAllReminderEntityUseCase {
        return GetAllReminderEntityUseCase(reminderRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetAllReminderUseCase(
        getAllReminderEntityUseCase: GetAllReminderEntityUseCase
    ): GetAllReminderUseCase {
        return GetAllReminderUseCase(getAllReminderEntityUseCase)
    }
}