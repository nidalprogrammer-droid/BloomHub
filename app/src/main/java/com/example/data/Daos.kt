package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users LIMIT 1")
    fun getUser(): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)
}

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity): Long

    @Query("UPDATE tasks SET isCompleted = :isCompleted WHERE taskId = :taskId")
    suspend fun updateTaskStatus(taskId: Int, isCompleted: Boolean)
}

@Dao
interface SubTaskDao {
    @Query("SELECT * FROM sub_tasks WHERE parentTaskId = :taskId")
    fun getSubTasksForTask(taskId: Int): Flow<List<SubTaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubTask(subTask: SubTaskEntity)
    
    @Query("UPDATE sub_tasks SET isCompleted = :isCompleted WHERE subTaskId = :subTaskId")
    suspend fun updateSubTaskStatus(subTaskId: Int, isCompleted: Boolean)
}

@Dao
interface MoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMood(mood: DailyMoodEntity)
}
