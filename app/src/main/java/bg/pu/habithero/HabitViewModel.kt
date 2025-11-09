package bg.pu.habithero

import androidx.lifecycle.*
import bg.pu.habithero.data.local.entity.Habit
import bg.pu.habithero.data.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(
    private val repo: HabitRepository
) : ViewModel() {

    val habits: LiveData<List<Habit>> = repo.getHabits().asLiveData()

    fun addHabit(habit: Habit) = viewModelScope.launch {
        repo.addHabit(habit)
    }

    fun updateHabit(habit: Habit) = viewModelScope.launch {
        repo.updateHabit(habit)
    }

    fun deleteHabit(habit: Habit) = viewModelScope.launch {
        repo.deleteHabit(habit)
    }
}
