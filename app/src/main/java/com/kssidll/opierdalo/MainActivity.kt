package com.kssidll.opierdalo

import android.Manifest
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.kssidll.opierdalo.domain.notification.ReminderNotificationScheduler
import com.kssidll.opierdalo.domain.notification.ReminderNotificationScheduler.Companion.rememberPreparationLauncher
import com.kssidll.opierdalo.domain.preferences.AppPreferences
import com.kssidll.opierdalo.domain.preferences.detectDarkMode
import com.kssidll.opierdalo.helper.checkPermission
import com.kssidll.opierdalo.ui.theme.OpierdaloTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
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

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val launcher = rememberPreparationLauncher(
                    onSuccess = {
                        if (checkPermission(this, Manifest.permission.POST_NOTIFICATIONS)) {
                            ReminderNotificationScheduler(this).start()
                        }
                    },
                    onFailure = {}
                )

                LaunchedEffect(Unit) {
                    launcher.launchPermissionRequest()
                }
            } else {
                LaunchedEffect(Unit) {
                    ReminderNotificationScheduler(this@MainActivity).start()
                }
            }

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

