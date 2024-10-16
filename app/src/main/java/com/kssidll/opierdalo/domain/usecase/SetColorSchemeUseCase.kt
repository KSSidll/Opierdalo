package com.kssidll.opierdalo.domain.usecase

import android.content.Context
import com.kssidll.opierdalo.domain.preferences.AppPreferences

class SetColorSchemeUseCase(
    private val context: Context
) {
    suspend operator fun invoke(
        newColorScheme: AppPreferences.Theme.ColorScheme.Values
    ) {
        AppPreferences.setColorScheme(
            context,
            newColorScheme
        )
    }
}