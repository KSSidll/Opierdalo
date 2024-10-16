package com.kssidll.opierdalo.domain.usecase

import androidx.appcompat.app.AppCompatDelegate.setApplicationLocales
import androidx.core.os.LocaleListCompat
import com.kssidll.opierdalo.domain.AppLocale

class SetLocaleUseCase {
    operator fun invoke(
        locale: AppLocale?
    ) {
        val localeList = if (locale != null) {
            LocaleListCompat.forLanguageTags(locale.tag)
        } else LocaleListCompat.getEmptyLocaleList()

        setApplicationLocales(localeList)
    }
}