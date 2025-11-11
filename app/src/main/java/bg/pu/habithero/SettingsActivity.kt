package bg.pu.habithero

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import bg.pu.habithero.databinding.ScreenSettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ScreenSettingsBinding
    private val prefs by lazy { PreferenceManager.getDefaultSharedPreferences(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ScreenSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Нотификации
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

        // Тъмна тема
        val isDark = prefs.getBoolean("pref_dark_theme", false)
        binding.switchDarkThemeCore.isChecked = isDark

        binding.switchDarkThemeCore.setOnCheckedChangeListener { _, checked ->
            prefs.edit().putBoolean("pref_dark_theme", checked).apply()

            AppCompatDelegate.setDefaultNightMode(
                if (checked) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
            )
        }

        binding.btnBackFromSettingsCore.setOnClickListener {
            finish()
        }
    }
}
