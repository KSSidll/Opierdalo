package com.kssidll.opierdalo.di.module

import android.content.Context
import com.kssidll.opierdalo.domain.usecase.SetColorSchemeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class SetThemeUseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideSetThemeUseCase(
        @ApplicationContext context: Context
    ): SetColorSchemeUseCase {
        return SetColorSchemeUseCase(context)
    }
}