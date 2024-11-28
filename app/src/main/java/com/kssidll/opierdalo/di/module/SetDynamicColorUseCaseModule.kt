package com.kssidll.opierdalo.di.module

import android.content.Context
import com.kssidll.opierdalo.domain.usecase.SetDynamicColorUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SetDynamicColorUseCaseModule {
    @Provides
    fun provideSetDynamicColorUseCase(
        @ApplicationContext context: Context
    ): SetDynamicColorUseCase {
        return SetDynamicColorUseCase(context)
    }
}