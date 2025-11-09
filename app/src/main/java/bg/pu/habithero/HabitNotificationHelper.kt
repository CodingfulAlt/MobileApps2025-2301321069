package bg.pu.habithero

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object HabitNotificationHelper {

    fun showEnabledNotification(context: Context) {
        // Проверка за POST_NOTIFICATIONS
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val builder = NotificationCompat.Builder(context, "habit_reminder_channel")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("HabitHero")
            .setContentText("Браво! Активира дневните напомняния.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        NotificationManagerCompat.from(context).notify(999, builder.build())
    }
}
