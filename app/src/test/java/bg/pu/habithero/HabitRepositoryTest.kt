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
    fun testAddHabitCallsDaoInsert() = runBlocking {
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
}
