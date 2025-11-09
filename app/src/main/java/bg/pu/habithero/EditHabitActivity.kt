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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ScreenEditHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnSaveHabitCore.setOnClickListener {
            val name = binding.inputHabitNameCore.text.toString().trim()
            val desc = binding.inputHabitDescriptionCore.text.toString().trim()
            val goalText = binding.inputGoalPerDayCore.text.toString().trim()

            // validation
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

            val habit = Habit(
                name = name,
                description = if (desc.isEmpty()) null else desc,
                goalPerDay = goal,
                reminderHour = null,
                icon = null,
                createdAt = System.currentTimeMillis()
            )

            vm.addHabit(habit)
            Toast.makeText(this, "Навик запазен", Toast.LENGTH_SHORT).show()
          
            finish()
        }
    }
}
