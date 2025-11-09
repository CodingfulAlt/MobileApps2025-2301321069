package bg.pu.habithero

import android.os.Bundle
import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import bg.pu.habithero.databinding.ScreenHabitDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HabitDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ScreenHabitDetailsBinding
    private val vm: HabitViewModel by viewModels()

    private var currentHabit: bg.pu.habithero.data.local.entity.Habit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ScreenHabitDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("habit_id", -1)
        if (id == -1) {
            binding.textDetailsNameCore.text = "Няма навик"
            return
        }

        vm.habits.observe(this) { list ->
            val h = list.firstOrNull { it.id == id }
            if (h != null) {
                currentHabit = h
                binding.textDetailsNameCore.text = h.name

                // Тук вече слагаме само съдържанието, без "Описание:"
                val descText = if (h.description.isNullOrBlank()) {
                    "(няма въведено описание)"
                } else {
                    h.description
                }
                binding.textDetailsDescriptionCore.text = descText

                // Само число + "пъти" – етикетът е в отделния TextView
                binding.textDetailsGoalCore.text = "${h.goalPerDay} пъти"
            } else {
                binding.textDetailsNameCore.text = "Няма навик"
                binding.textDetailsDescriptionCore.text = ""
                binding.textDetailsGoalCore.text = ""
            }
        }

        binding.btnBackFromDetails.setOnClickListener { finish() }

        binding.btnEditHabitCore.setOnClickListener {
            val habit = currentHabit ?: return@setOnClickListener
            val i = Intent(this, EditHabitActivity::class.java).apply {
                putExtra("habit_id", habit.id)
                putExtra("habit_name", habit.name)
                putExtra("habit_desc", habit.description)
                putExtra("habit_goal", habit.goalPerDay)
            }
            startActivity(i)
        }

        binding.btnCompleteHabitCore.setOnClickListener {
            currentHabit?.let { habit ->
                vm.deleteHabit(habit)
                finish()
            }
        }
    }
}
