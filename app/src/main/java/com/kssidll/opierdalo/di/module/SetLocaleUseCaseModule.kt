package com.kssidll.opierdalo.di.module

import com.kssidll.opierdalo.domain.usecase.SetLocaleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SetLocaleUseCaseModule {
    @Provides
    fun provideSetLocaleUseCase(): SetLocaleUseCase {
        return SetLocaleUseCase()
    }
}