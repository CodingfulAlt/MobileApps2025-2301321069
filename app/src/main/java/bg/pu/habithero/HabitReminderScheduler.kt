package bg.pu.habithero

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.preference.PreferenceManager
import java.util.Calendar

object HabitReminderScheduler {

    private const val REQUEST_CODE = 2001
    private const val PREF_KEY_ENABLED = "habit_daily_reminder_enabled"

    private fun buildPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, HabitReminderReceiver::class.java)
        return PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun enableDailyReminder(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 20)  // 20:00
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DATE, 1)
            }
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            buildPendingIntent(context)
        )

        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putBoolean(PREF_KEY_ENABLED, true)
            .apply()
    }

    fun disableDailyReminder(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(buildPendingIntent(context))

        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putBoolean(PREF_KEY_ENABLED, false)
            .apply()
    }

    fun isEnabled(context: Context): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean(PREF_KEY_ENABLED, false)
    }
}
