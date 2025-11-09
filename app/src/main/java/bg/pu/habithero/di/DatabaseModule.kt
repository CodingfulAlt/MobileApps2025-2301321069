package bg.pu.habithero.di

import android.content.Context
import androidx.room.Room
import bg.pu.habithero.data.local.dao.HabitDao
import bg.pu.habithero.data.local.db.AppDatabase
import bg.pu.habithero.data.repository.HabitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "habit_hero_db"
        ).build()
    }

    @Provides
    fun provideHabitDao(db: AppDatabase): HabitDao = db.habitDao()

    @Provides
    @Singleton
    fun provideHabitRepository(
        habitDao: HabitDao
    ): HabitRepository = HabitRepository(habitDao)
}
