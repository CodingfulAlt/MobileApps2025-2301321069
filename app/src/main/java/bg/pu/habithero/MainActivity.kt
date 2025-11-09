package bg.pu.habithero

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import bg.pu.habithero.databinding.ScreenHabitHubBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ScreenHabitHubBinding
    private val vm: HabitViewModel by viewModels()
    private lateinit var adapter: HabitListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ScreenHabitHubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = HabitListAdapter(
            onClick = { habit ->
                val i = Intent(this, HabitDetailsActivity::class.java)
                i.putExtra("habit_id", habit.id)
                startActivity(i)
            },
            onLongClick = { habit ->
                // за момента директно трием; по-нататък може да сложим диалог
                vm.deleteHabit(habit)
            }
        )

        // ТУК ползваме recyclerHabits от XML, НЕ създаваме нов RecyclerView
        binding.recyclerHabits.layoutManager = LinearLayoutManager(this)
        binding.recyclerHabits.adapter = adapter

        vm.habits.observe(this) { list ->
            adapter.submitList(list)
            binding.textTitleHabitHub.text =
                if (list.isEmpty()) "Няма навици" else "HabitHero – Навици"
        }

        binding.btnAddNewHabitCore.setOnClickListener {
            startActivity(Intent(this, EditHabitActivity::class.java))
        }
    }
}
