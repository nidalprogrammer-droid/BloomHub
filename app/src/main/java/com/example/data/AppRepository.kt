package com.example.data

import kotlinx.coroutines.flow.Flow

class AppRepository(private val db: AppDatabase) {
    val userFlow: Flow<UserEntity?> = db.userDao().getUser()
    val allTasksFlow: Flow<List<TaskEntity>> = db.taskDao().getAllTasks()

    suspend fun createUser(name: String) {
        val uniqueId = "USER_" + java.util.UUID.randomUUID().toString().substring(0, 8)
        db.userDao().insertUser(UserEntity(userId = uniqueId, userName = name))
    }

    suspend fun addTask(userId: String, name: String, isUrgent: Boolean, deadline: Long?) {
        val taskId = db.taskDao().insertTask(TaskEntity(userId = userId, taskName = name, isUrgent = isUrgent, deadline = deadline))
        
        // Auto Task Chunking (if it's a big task)
        val lowerName = name.lowercase()
        if (lowerName.contains("study") || lowerName.contains("essay") || lowerName.contains("project") || name.length > 20) {
            val subTasks = listOf(
                SubTaskEntity(parentTaskId = taskId.toInt(), subTaskName = "1. Gather materials & open notes"),
                SubTaskEntity(parentTaskId = taskId.toInt(), subTaskName = "2. Review for 15 minutes"),
                SubTaskEntity(parentTaskId = taskId.toInt(), subTaskName = "3. Draft initial outline/thoughts"),
                SubTaskEntity(parentTaskId = taskId.toInt(), subTaskName = "4. Finalize session goals")
            )
            for (sub in subTasks) {
                db.subTaskDao().insertSubTask(sub)
            }
        }
    }

    suspend fun updateTaskStatus(taskId: Int, isCompleted: Boolean) {
        db.taskDao().updateTaskStatus(taskId, isCompleted)
    }

    suspend fun addVoiceJournal(transcript: String) {
        db.moodDao().insertMood(DailyMoodEntity(transcript = transcript))
    }
}
