package bg.pu.habithero.data.repository

import bg.pu.habithero.data.local.dao.HabitDao
import bg.pu.habithero.data.local.entity.Habit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabitRepository @Inject constructor(
    private val habitDao: HabitDao
) {

    fun getHabits(): Flow<List<Habit>> = habitDao.getAllHabits()

    fun getHabitById(id: Int): Flow<Habit?> = habitDao.getHabitById(id)

    suspend fun addHabit(habit: Habit) {
        habitDao.insertHabit(habit)
    }

    suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit)
    }

    suspend fun deleteHabit(habit: Habit) {
        habitDao.deleteHabit(habit)
    }
}
