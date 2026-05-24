package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val userId: String,
    val userName: String,
    val activeStatus: String = "offline",
    val isBanned: Boolean = false,
    val graceDaysLeft: Int = 2
)

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val taskId: Int = 0,
    val userId: String,
    val taskName: String,
    val isUrgent: Boolean = false,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val deadline: Long? = null
)

@Entity(tableName = "sub_tasks")
data class SubTaskEntity(
    @PrimaryKey(autoGenerate = true) val subTaskId: Int = 0,
    val parentTaskId: Int,
    val subTaskName: String,
    val isCompleted: Boolean = false
)

@Entity(tableName = "daily_moods")
data class DailyMoodEntity(
    @PrimaryKey(autoGenerate = true) val moodId: Int = 0,
    val transcript: String,
    val createdAt: Long = System.currentTimeMillis()
)
