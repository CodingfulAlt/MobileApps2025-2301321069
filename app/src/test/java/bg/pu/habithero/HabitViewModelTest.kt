package bg.pu.habithero

import bg.pu.habithero.data.local.entity.Habit
import bg.pu.habithero.data.repository.HabitRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class HabitViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repo: HabitRepository = mock()

    // mock getHabits() to avoid NullPointerException
    private val vm: HabitViewModel

    init {
        whenever(repo.getHabits()).thenReturn(flowOf(emptyList()))
        vm = HabitViewModel(repo)
    }

    @Test
    fun addHabit_callsRepository() = runTest {
        val habit = Habit(
            id = 1,
            name = "VM тест",
            description = "описание",
            goalPerDay = 3,
            reminderHour = null,
            icon = null,
            createdAt = 0
        )

        vm.addHabit(habit)

        verify(repo).addHabit(habit)
    }
}
