package com.kssidll.opierdalo.domain.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.AlarmManagerCompat
import androidx.core.app.PendingIntentCompat
import androidx.core.content.ContextCompat
import com.kssidll.opierdalo.domain.usecase.reminder.GetAllReminderUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class ReminderNotificationShowActionReceiver: BroadcastReceiver() {
    @Inject
    lateinit var getAllReminderUseCase: GetAllReminderUseCase
    private var receiverJob: Job? = null

    override fun onReceive(
        context: Context?,
        intent: Intent?
    ) {
        context?.let {
            receiverJob?.cancel()
            receiverJob = CoroutineScope(Dispatchers.IO).launch {
                val reminderList = getAllReminderUseCase().first()

                if (reminderList.isNotEmpty()) {
                    ReminderNotification.show(it, reminderList)
                }

                schedule(it)
            }
        }
    }

    companion object {
        private const val TAG = "notif_show_rec"

        fun schedule(context: Context) {
            val alarmManager = ContextCompat.getSystemService(context, AlarmManager::class.java)

            val intent = Intent(context, ReminderNotificationShowActionReceiver::class.java)
            val pendingIntent = PendingIntentCompat.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT,
                false
            )!! // null not possible unless flags includes FLAG_NO_CREATE

            alarmManager?.let {
                val delay = getMillisecondsToNearestEvenTime(
                    skipHoursStart = 22,
                    skipHoursEnd = 9
                )

                AlarmManagerCompat.setExactAndAllowWhileIdle(
                    it,
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + delay,
                    pendingIntent
                )

                Log.i(TAG, "Scheduled a notification with a delay of $delay ms")
            }
        }
    }
}

fun getMillisecondsToNearestEvenTime(skipHoursStart: Int, skipHoursEnd: Int): Long {
    // Get current time
    val now = Calendar.getInstance()

    // Create a copy of current time to manipulate
    val nextEvenTime = Calendar.getInstance()

    // Calculate minutes to next even time (30-minute interval)
    val currentMinutes = now.get(Calendar.MINUTE)

    if (currentMinutes < 30) {
        // Set to 30 minutes past current hour
        nextEvenTime.set(Calendar.MINUTE, 30)
        nextEvenTime.set(Calendar.SECOND, 0)
        nextEvenTime.set(Calendar.MILLISECOND, 0)
    } else {
        // Set to start of next hour
        nextEvenTime.set(Calendar.MINUTE, 0)
        nextEvenTime.set(Calendar.SECOND, 0)
        nextEvenTime.set(Calendar.MILLISECOND, 0)
        nextEvenTime.add(Calendar.HOUR_OF_DAY, 1)
    }

    // Offset disabled hours
    val nextEvenTimeHours = now.get(Calendar.HOUR_OF_DAY)

    if (nextEvenTimeHours >= skipHoursStart) {
        nextEvenTime.set(Calendar.MINUTE, 0)
        nextEvenTime.set(Calendar.HOUR_OF_DAY, skipHoursEnd)
        nextEvenTime.add(Calendar.DAY_OF_YEAR, 1)
    } else if (nextEvenTimeHours < skipHoursEnd) {
        nextEvenTime.set(Calendar.MINUTE, 0)
        nextEvenTime.set(Calendar.HOUR_OF_DAY, skipHoursEnd)
    }

    // Calculate and return milliseconds difference
    return nextEvenTime.timeInMillis - now.timeInMillis
}
