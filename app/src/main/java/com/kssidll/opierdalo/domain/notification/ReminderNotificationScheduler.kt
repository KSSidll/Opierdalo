package com.kssidll.opierdalo.domain.notification

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import com.kssidll.opierdalo.R

@Suppress("MemberVisibilityCanBePrivate")
object ReminderNotification {
    fun notification(context: Context) = NotificationCompat.Builder(
        context,
        CHANNEL_ID
    )
        .setContentTitle(CONTENT_TITLE)
        .setContentText(CONTENT_TEXT)
        .setPriority(NOTIFICATION_PRIORITY)
        .setSmallIcon(R.drawable.ic_stat_name)
        .build()

    fun show(context: Context) {
        val notification = notification(context)

        with(NotificationManagerCompat.from(context)) {
            try {
                notify(0, notification)
            } catch (_: SecurityException) {
            }
        }
    }

    fun notificationChannel() = NotificationChannelCompat.Builder(
        CHANNEL_ID,
        CHANNEL_IMPORTANCE
    )
        .setDescription(CHANNEL_DESCRIPTION)
        .setName(CHANNEL_NAME)
        .setLightsEnabled(true)
        .setVibrationEnabled(true)
        .setVibrationPattern(longArrayOf(1000, 1000, 1000))
        .build()

    const val CHANNEL_ID = "reminders_channel"
    const val CHANNEL_NAME = "Reminder"
    const val CHANNEL_DESCRIPTION = "Channel for reminders"
    const val CHANNEL_IMPORTANCE = NotificationManagerCompat.IMPORTANCE_MAX
    const val CONTENT_TITLE = "Przestań się opierdalać"
    const val CONTENT_TEXT = "chuju głupi"
    const val NOTIFICATION_PRIORITY = NotificationCompat.PRIORITY_MAX
}


class ReminderNotificationScheduler(
    private val context: Context
) {
    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = ReminderNotification.notificationChannel()

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.createNotificationChannel(channel)
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun start() {
        Log.i(TAG, "Started the ReminderNotificationScheduler")

        ReminderNotificationShowActionReceiver.schedule(context)
    }

    companion object {
        const val TAG = "notification_scheduler"

        @OptIn(ExperimentalPermissionsApi::class)
        @SuppressLint("ComposeNamingUppercase", "ComposableNaming")
        @Composable
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        @Stable
        fun rememberPreparationLauncher(
            onSuccess: () -> Unit,
            onFailure: () -> Unit
        ): PermissionState {
            return rememberPermissionState(
                permission = Manifest.permission.POST_NOTIFICATIONS,
                onPermissionResult = { result ->
                    if (result) {
                        onSuccess()
                    } else {
                        onFailure()
                    }
                }
            )
        }
    }
}
