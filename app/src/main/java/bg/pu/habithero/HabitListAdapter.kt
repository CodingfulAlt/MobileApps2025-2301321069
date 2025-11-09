package bg.pu.habithero

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import bg.pu.habithero.data.local.entity.Habit

class HabitListAdapter(
    private val onClick: (Habit) -> Unit,
    private val onLongClick: (Habit) -> Unit
) : ListAdapter<Habit, HabitListAdapter.HabitVH>(HabitDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_habit, parent, false)
        return HabitVH(view, onClick, onLongClick)
    }

    override fun onBindViewHolder(holder: HabitVH, position: Int) {
        holder.bind(getItem(position))
    }

    class HabitVH(
        itemView: View,
        val onClick: (Habit) -> Unit,
        val onLongClick: (Habit) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.textItemName)
        private val desc: TextView = itemView.findViewById(R.id.textItemDescription)
        private val goal: TextView = itemView.findViewById(R.id.textItemGoal)

        private var current: Habit? = null

        init {
            itemView.setOnClickListener {
                current?.let { onClick(it) }
            }
            itemView.setOnLongClickListener {
                current?.let { onLongClick(it) }
                true
            }
        }

        fun bind(h: Habit) {
            current = h
            name.text = h.name
            desc.text = h.description ?: ""
            goal.text = "${h.goalPerDay}x"
        }
    }

    class HabitDiffCallback : DiffUtil.ItemCallback<Habit>() {
        override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean =
            oldItem == newItem
    }
}
