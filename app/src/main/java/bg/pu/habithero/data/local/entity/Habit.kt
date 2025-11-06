package bg.pu.habithero.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String?,
    val goalPerDay: Int,
    val reminderHour: Int?,   // 0..23
    val icon: String?,        // emoji или име на икона
    val createdAt: Long       // System.currentTimeMillis()
)
