package bg.pu.habithero.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import bg.pu.habithero.data.local.dao.HabitDao
import bg.pu.habithero.data.local.entity.Habit

@Database(
    entities = [Habit::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}
