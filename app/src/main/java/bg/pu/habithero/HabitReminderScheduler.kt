package bg.pu.habithero

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.preference.PreferenceManager
import java.util.Calendar

object HabitReminderScheduler {

    private const val PREF_KEY_ENABLED = "habit_daily_reminder_enabled"
    private const val REQUEST_CODE_15 = 2001
    private const val REQUEST_CODE_20 = 2002

    private fun buildPendingIntent(context: Context, requestCode: Int): PendingIntent {
        val intent = Intent(context, HabitReminderReceiver::class.java)
        return PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun scheduleDailyAt(context: Context, hour: Int, requestCode: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val now = Calendar.getInstance()
        val triggerTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (before(now)) {
                add(Calendar.DATE, 1)
            }
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerTime.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            buildPendingIntent(context, requestCode)
        )
    }

    fun enableDailyReminder(context: Context) {
        scheduleDailyAt(context, 15, REQUEST_CODE_15)
        scheduleDailyAt(context, 20, REQUEST_CODE_20)

        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putBoolean(PREF_KEY_ENABLED, true)
            .apply()
    }

    fun disableDailyReminder(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(buildPendingIntent(context, REQUEST_CODE_15))
        alarmManager.cancel(buildPendingIntent(context, REQUEST_CODE_20))

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
