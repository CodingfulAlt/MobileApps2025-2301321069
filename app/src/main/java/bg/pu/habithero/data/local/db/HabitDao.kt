package bg.pu.habithero.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import bg.pu.habithero.data.local.entity.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM habits ORDER BY id DESC")
    fun getAllHabits(): Flow<List<Habit>>

    @Query("SELECT * FROM habits WHERE id = :id LIMIT 1")
    fun getHabitById(id: Int): Flow<Habit?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit)

    @Update
    suspend fun updateHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)
}
