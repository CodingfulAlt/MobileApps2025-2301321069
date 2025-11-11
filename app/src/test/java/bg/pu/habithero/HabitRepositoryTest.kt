package bg.pu.habithero

import bg.pu.habithero.data.local.dao.HabitDao
import bg.pu.habithero.data.local.entity.Habit
import bg.pu.habithero.data.repository.HabitRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class HabitRepositoryTest {

    private val dao = mock<HabitDao>()
    private val repo = HabitRepository(dao)

    @Test
    fun addHabit_callsDaoInsert() = runBlocking {
        val habit = Habit(
            name = "Тест навик",
            description = "Тест описание",
            goalPerDay = 2,
            reminderHour = null,
            icon = null,
            createdAt = 0
        )

        repo.addHabit(habit)

        verify(dao).insertHabit(habit)
    }

    @Test
    fun updateHabit_callsDaoUpdate() = runBlocking {
        val habit = Habit(
            id = 1,
            name = "Старо име",
            description = "Старо описание",
            goalPerDay = 3,
            reminderHour = null,
            icon = null,
            createdAt = 0
        )

        repo.updateHabit(habit)

        verify(dao).updateHabit(habit)
    }

    @Test
    fun deleteHabit_callsDaoDelete() = runBlocking {
        val habit = Habit(
            id = 1,
            name = "За изтриване",
            description = null,
            goalPerDay = 1,
            reminderHour = null,
            icon = null,
            createdAt = 0
        )

        repo.deleteHabit(habit)

        verify(dao).deleteHabit(habit)
    }
}
