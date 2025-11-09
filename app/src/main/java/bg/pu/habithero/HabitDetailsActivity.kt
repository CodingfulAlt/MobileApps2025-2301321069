package bg.pu.habithero

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import bg.pu.habithero.databinding.ScreenHabitDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HabitDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ScreenHabitDetailsBinding
    private val vm: HabitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ScreenHabitDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("habit_id", -1)
        if (id == -1) {
            binding.textDetailsNameCore.text = "Няма навик"
            return
        }

        // observe one by id
        vm.habits.observe(this) { list ->
            val h = list.firstOrNull { it.id == id }
            if (h != null) {
                binding.textDetailsNameCore.text = h.name
                binding.textDetailsDescriptionCore.text = h.description ?: ""
            }
        }

        binding.btnBackFromDetails.setOnClickListener { finish() }
    }
}
