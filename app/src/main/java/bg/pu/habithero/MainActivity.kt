package bg.pu.habithero

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import bg.pu.habithero.databinding.ScreenHabitHubBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

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

        val swipeToDeleteCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val habit = adapter.currentList[viewHolder.adapterPosition]
                vm.deleteHabit(habit)
            }
        }

        ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(binding.recyclerHabits)

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
