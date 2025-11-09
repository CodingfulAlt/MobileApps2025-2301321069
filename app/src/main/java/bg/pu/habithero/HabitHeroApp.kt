package bg.pu.habithero

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HabitHeroApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "habit_reminder_channel",
                "Habit reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Дневни напомняния за навиците ти"
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}
