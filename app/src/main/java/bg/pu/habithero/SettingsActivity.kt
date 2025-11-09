package bg.pu.habithero

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bg.pu.habithero.databinding.ScreenSettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ScreenSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ScreenSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Първоначално състояние според pref
        binding.switchNotificationsCore.isChecked =
            HabitReminderScheduler.isEnabled(this)

        binding.switchNotificationsCore.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                HabitReminderScheduler.enableDailyReminder(this)
                HabitNotificationHelper.showEnabledNotification(this)
            } else {
                HabitReminderScheduler.disableDailyReminder(this)
            }
        }

        binding.btnBackFromSettingsCore.setOnClickListener {
            finish()
        }
    }
}
