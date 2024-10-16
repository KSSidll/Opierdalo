package com.kssidll.opierdalo.domain.usecase

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.kssidll.opierdalo.domain.preferences.AppPreferences

class SetDynamicColorUseCase(
    private val context: Context
) {
    @RequiresApi(Build.VERSION_CODES.S)
    suspend operator fun invoke(
        newDynamicColor: Boolean
    ) {
        AppPreferences.setDynamicColor(
            context,
            newDynamicColor
        )
    }
}