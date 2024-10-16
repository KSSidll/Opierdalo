package com.kssidll.opierdalo.domain

// for same reason [Locale] doesn't work as intended
/**
 * Application supported locales
 * @param code ISO 639-2 language code of the locale
 * @param tag Language tag of the locale
 */
enum class AppLocale(
    val code: String,
    val tag: String
) {
    PL(
        "pol",
        "pl-PL"
    ),
    EN_US(
        "eng",
        "en-US"
    )
}