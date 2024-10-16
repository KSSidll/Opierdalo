package com.kssidll.opierdalo.di.module

import android.content.Context
import com.kssidll.opierdalo.domain.usecase.SetDynamicColorUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class SetDynamicColorUseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideSetDynamicColorUseCase(
        @ApplicationContext context: Context
    ): SetDynamicColorUseCase {
        return SetDynamicColorUseCase(context)
    }
}