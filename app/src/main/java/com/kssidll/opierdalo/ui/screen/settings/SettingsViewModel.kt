package com.kssidll.opierdalo.ui.screen.settings


import android.content.Context
import android.os.Build
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kssidll.opierdalo.domain.AppLocale
import com.kssidll.opierdalo.domain.preferences.AppPreferences
import com.kssidll.opierdalo.domain.usecase.SetColorSchemeUseCase
import com.kssidll.opierdalo.domain.usecase.SetDynamicColorUseCase
import com.kssidll.opierdalo.domain.usecase.SetLocaleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
data class SettingsUiState(
    val isInDynamicColor: Boolean = false,
    val currentTheme: AppPreferences.Theme.ColorScheme.Values = AppPreferences.Theme.ColorScheme.DEFAULT,
)

@Immutable
sealed class SettingsEvent {
    data class SetLocale(val locale: AppLocale?): SettingsEvent()
    data class SetColorScheme(val newColorScheme: AppPreferences.Theme.ColorScheme.Values):
        SettingsEvent()

    data class SetDynamicColor(val newDynamicColor: Boolean): SettingsEvent()
    data object NavigateBack: SettingsEvent()
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val setLocaleUseCase: SetLocaleUseCase,
    private val setColorSchemeUseCase: SetColorSchemeUseCase,
    private val setDynamicColorUseCase: SetDynamicColorUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(
        SettingsUiState()
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            AppPreferences.getColorScheme(context).collect {
                _uiState.update { currentState ->
                    currentState.copy(
                        currentTheme = it
                    )
                }
            }
        }

        viewModelScope.launch {
            AppPreferences.getDynamicColor(context).collect {
                _uiState.update { currentState ->
                    currentState.copy(
                        isInDynamicColor = it
                    )
                }
            }
        }
    }

    fun handleEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.SetLocale -> setLocale(event.locale)
            is SettingsEvent.SetColorScheme -> setColorScheme(event.newColorScheme)
            is SettingsEvent.SetDynamicColor -> setDynamicColor(event.newDynamicColor)

            is SettingsEvent.NavigateBack -> {}
        }
    }

    private fun setLocale(locale: AppLocale?) {
        setLocaleUseCase(locale)
    }

    private fun setColorScheme(newColorScheme: AppPreferences.Theme.ColorScheme.Values) =
        viewModelScope.launch {
            setColorSchemeUseCase(newColorScheme)
        }

    private fun setDynamicColor(newDynamicColor: Boolean) = viewModelScope.launch {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setDynamicColorUseCase(newDynamicColor)
        }
    }
}
