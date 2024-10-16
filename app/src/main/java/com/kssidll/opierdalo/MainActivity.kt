package com.kssidll.opierdalo

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kssidll.opierdalo.domain.preferences.AppPreferences
import com.kssidll.opierdalo.domain.preferences.detectDarkMode
import com.kssidll.opierdalo.ui.theme.OpierdaloTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        val colorScheme: AppPreferences.Theme.ColorScheme.Values
        val isInDynamicColor: Boolean

        runBlocking {
            colorScheme = AppPreferences.getColorScheme(applicationContext).first()
            isInDynamicColor = AppPreferences.getDynamicColor(applicationContext).first()
        }

        setContent {
            val appColorScheme =
                AppPreferences.getColorScheme(applicationContext).collectAsState(colorScheme).value

            OpierdaloTheme(
                appColorScheme = appColorScheme,
                isInDynamicColor = AppPreferences.getDynamicColor(applicationContext)
                    .collectAsState(isInDynamicColor).value
            ) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                        appColorScheme.detectDarkMode()
                    ),
                    navigationBarStyle = SystemBarStyle.auto(
                        Color.argb(0xe6, 0xFF, 0xFF, 0xFF),
                        Color.argb(0x80, 0x1b, 0x1b, 0x1b),
                        appColorScheme.detectDarkMode()
                    )
                )

                Navigation(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

