package com.kssidll.opierdalo.di.module

import android.content.Context
import com.kssidll.opierdalo.domain.usecase.SetColorSchemeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SetThemeUseCaseModule {
    @Provides
    fun provideSetThemeUseCase(
        @ApplicationContext context: Context
    ): SetColorSchemeUseCase {
        return SetColorSchemeUseCase(context)
    }
}