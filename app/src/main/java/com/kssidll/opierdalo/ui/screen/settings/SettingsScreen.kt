package com.kssidll.opierdalo.ui.screen.settings

import android.os.Build
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.kssidll.opierdalo.ExpandedPreviews
import com.kssidll.opierdalo.R
import com.kssidll.opierdalo.ui.component.SecondaryAppBar
import com.kssidll.opierdalo.ui.screen.settings.component.LanguageExposedDropdown
import com.kssidll.opierdalo.ui.screen.settings.component.ThemeExposedDropdown
import com.kssidll.opierdalo.ui.theme.OpierdaloTheme
import com.kssidll.opierdalo.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    onEvent: (event: SettingsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            SecondaryAppBar(
                onBack = {
                    onEvent(SettingsEvent.NavigateBack)
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.settings),
                        style = Typography.titleLarge,
                    )
                },
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                LanguageExposedDropdown(
                    setLocale = {
                        onEvent(SettingsEvent.SetLocale(it))
                    }
                )


                ThemeExposedDropdown(
                    currentTheme = uiState.currentTheme,
                    setTheme = {
                        onEvent(SettingsEvent.SetColorScheme(it))
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    val dynamicThemeInteractionSource = remember {
                        MutableInteractionSource()
                    }

                    Surface(
                        shape = ShapeDefaults.Large,
                        tonalElevation = 2.dp,
                        interactionSource = dynamicThemeInteractionSource,
                        onClick = {
                            onEvent(SettingsEvent.SetDynamicColor(!uiState.isInDynamicColor))
                        },
                        modifier = Modifier
                            .width(TextFieldDefaults.MinWidth)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                        ) {
                            Checkbox(
                                checked = uiState.isInDynamicColor,
                                interactionSource = dynamicThemeInteractionSource,
                                onCheckedChange = {
                                    onEvent(SettingsEvent.SetDynamicColor(!uiState.isInDynamicColor))
                                }
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = stringResource(R.string.settings_dynamic_theme),
                                style = Typography.labelMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@ExpandedPreviews
@Composable
private fun SettingsScreenPreview() {
    OpierdaloTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SettingsScreen(
                uiState = SettingsUiState(),
                onEvent = {}
            )
        }
    }
}
