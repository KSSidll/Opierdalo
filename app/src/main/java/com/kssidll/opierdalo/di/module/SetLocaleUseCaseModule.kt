package com.kssidll.opierdalo.di.module

import com.kssidll.opierdalo.domain.usecase.SetLocaleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class SetLocaleUseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideSetLocaleUseCase(): SetLocaleUseCase {
        return SetLocaleUseCase()
    }
}