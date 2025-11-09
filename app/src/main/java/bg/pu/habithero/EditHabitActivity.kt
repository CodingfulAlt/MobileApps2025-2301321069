package bg.pu.habithero

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import bg.pu.habithero.data.local.entity.Habit
import bg.pu.habithero.databinding.ScreenEditHabitBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditHabitActivity : AppCompatActivity() {

    private lateinit var binding: ScreenEditHabitBinding
    private val vm: HabitViewModel by viewModels()

    // null → нов навик, != null → редакция
    private var currentHabit: Habit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ScreenEditHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val habitId = intent.getIntExtra("habit_id", -1)
        if (habitId != -1) {
            binding.btnSaveHabitCore.text = "Запази промените"

            vm.habits.observe(this) { list ->
                val h = list.firstOrNull { it.id == habitId }
                if (h != null) {
                    currentHabit = h
                    binding.inputHabitNameCore.setText(h.name)
                    binding.inputHabitDescriptionCore.setText(h.description ?: "")
                    binding.inputGoalPerDayCore.setText(h.goalPerDay.toString())
                }
            }
        }

        binding.btnSaveHabitCore.setOnClickListener {
            val name = binding.inputHabitNameCore.text.toString().trim()
            val desc = binding.inputHabitDescriptionCore.text.toString().trim()
            val goalText = binding.inputGoalPerDayCore.text.toString().trim()

            if (name.isEmpty()) {
                binding.inputHabitNameCore.error = "Въведи име"
                return@setOnClickListener
            }

            val goal = goalText.toIntOrNull()
            if (goal == null || goal <= 0) {
                binding.inputGoalPerDayCore.error = "Въведи положително число"
                return@setOnClickListener
            }
            if (goal > 20) {
                binding.inputGoalPerDayCore.error = "Максимум 20 пъти на ден"
                return@setOnClickListener
            }

            val habitToSave = currentHabit?.copy(
                name = name,
                description = if (desc.isEmpty()) null else desc,
                goalPerDay = goal
            ) ?: Habit(
                name = name,
                description = if (desc.isEmpty()) null else desc,
                goalPerDay = goal,
                reminderHour = null,
                icon = null,
                createdAt = System.currentTimeMillis()
            )

            if (currentHabit == null) {
                vm.addHabit(habitToSave)
            } else {
                vm.updateHabit(habitToSave)
            }

            Toast.makeText(this, "Навик запазен", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnBackFromEditHabitCore.setOnClickListener { finish() }
    }
}
