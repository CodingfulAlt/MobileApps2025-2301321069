package bg.pu.habithero

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class HabitReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {

        // За Android 13+ трябва да проверим POST_NOTIFICATIONS
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Нямаме разрешение → излизаме тихо
            return
        }

        val builder = NotificationCompat.Builder(context, "habit_reminder_channel")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("HabitHero")
            .setContentText("Не забравяй навиците си днес!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(1001, builder.build())
        }
    }
}
