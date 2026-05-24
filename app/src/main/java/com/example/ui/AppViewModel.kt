package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.AppRepository
import com.example.data.TaskEntity
import com.example.data.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class AppViewModel(private val repository: AppRepository) : ViewModel() {

    val currentUser: StateFlow<UserEntity?> = repository.userFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val tasks: StateFlow<List<TaskEntity>> = repository.allTasksFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _isOverwhelmed = MutableStateFlow(false)
    val isOverwhelmed: StateFlow<Boolean> = _isOverwhelmed

    fun setOverwhelmed(value: Boolean) {
        _isOverwhelmed.value = value
    }

    fun createUser(name: String) = viewModelScope.launch {
        repository.createUser(name)
    }

    fun addTask(name: String, isUrgent: Boolean, deadline: Long?) = viewModelScope.launch {
        currentUser.value?.let {
            repository.addTask(it.userId, name, isUrgent, deadline)
        }
    }

    fun toggleTask(taskId: Int, isCompleted: Boolean) = viewModelScope.launch {
        repository.updateTaskStatus(taskId, isCompleted)
    }

    fun addJournal(transcript: String) = viewModelScope.launch {
        repository.addVoiceJournal(transcript)
    }

    // Sunset mode Check (1 hour before bedtime, simulate bedtime at 22:00)
    fun isSunsetModeActive(): Boolean {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        return hour >= 21 // 21:00 or later
    }
}

class AppViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
