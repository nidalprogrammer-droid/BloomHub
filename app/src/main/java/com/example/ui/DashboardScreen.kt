package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.TaskEntity

@Composable
fun OnboardingScreen(onUserCreated: (String) -> Unit) {
    var name by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            shape = androidx.compose.foundation.shape.RoundedCornerShape(32.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, com.example.ui.theme.BorderLight, androidx.compose.foundation.shape.RoundedCornerShape(32.dp)),
            colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.CardBlue)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("BLOOMHUB", fontSize = 12.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp, color = com.example.ui.theme.SolidBlack.copy(alpha = 0.5f))
                Spacer(Modifier.height(16.dp))
                Text("A stress-free space for your mind.", textAlign = TextAlign.Center, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = com.example.ui.theme.SolidBlack, lineHeight = 32.sp)
                Spacer(Modifier.height(32.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("What should we call you?", fontSize = 12.sp) },
                    modifier = Modifier.fillMaxWidth().testTag("name_input"),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = com.example.ui.theme.SolidWhite,
                        unfocusedContainerColor = com.example.ui.theme.SolidWhite
                    )
                )
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = { if (name.isNotBlank()) onUserCreated(name) },
                    modifier = Modifier.fillMaxWidth().height(48.dp).testTag("onboarding_continue_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = com.example.ui.theme.SolidBlack, contentColor = com.example.ui.theme.SolidWhite),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp)
                ) {
                    Text("BEGIN", fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: AppViewModel) {
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    val isOverwhelmed by viewModel.isOverwhelmed.collectAsStateWithLifecycle()
    val user by viewModel.currentUser.collectAsStateWithLifecycle()
    
    val isSunset = viewModel.isSunsetModeActive()
    var newTaskName by remember { mutableStateOf("") }
    var newTaskUrgent by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column {
                Text("GOOD AFTERNOON", fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                Text(user?.userName ?: "[User Name]", fontSize = 30.sp, fontWeight = FontWeight.Bold, letterSpacing = (-1).sp)
                Text("ID: ${user?.userId ?: "Generating..."}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f))
            }
            Surface(
                color = MaterialTheme.colorScheme.onSurface,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
            ) {
                Text(
                    "LVL [04]",
                    color = MaterialTheme.colorScheme.surface,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
        
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { viewModel.setOverwhelmed(!isOverwhelmed) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSurface)
            ) {
                Text(if (isOverwhelmed) "Calm Mode" else "Overwhelmed?")
            }
        }
        
        if (isSunset) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(1.dp, com.example.ui.theme.BorderLight, androidx.compose.foundation.shape.RoundedCornerShape(24.dp)),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.CardGray)
            ) {
                Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("🌙", fontSize = 24.sp)
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text("SUNSET WIND-DOWN", fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                        Text("Activates in [Number] minutes", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        Text("Consider resting soon. Adding tasks for tomorrow.", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
                    }
                }
            }
        }
        
        Card(
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(horizontal = 16.dp)
                 .padding(bottom = 16.dp)
                 .border(1.dp, com.example.ui.theme.BorderLight, androidx.compose.foundation.shape.RoundedCornerShape(32.dp)),
             shape = androidx.compose.foundation.shape.RoundedCornerShape(32.dp),
             colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
         ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("ADD NEW TASK", fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newTaskName,
                        onValueChange = { newTaskName = it },
                        label = { Text("Task Name", fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Checkbox(checked = newTaskUrgent, onCheckedChange = { newTaskUrgent = it })
                        Text("Mark Urgent", fontSize = 12.sp)
                        Spacer(Modifier.width(8.dp))
                        
                        var deadline by remember { mutableStateOf<Long?>(null) }
                        var showDatePicker by remember { mutableStateOf(false) }

                        OutlinedButton(
                            onClick = { showDatePicker = true },
                            modifier = Modifier.height(32.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp)
                        ) {
                            val dateText = if (deadline != null) {
                            	val sdf = java.text.SimpleDateFormat("MMM dd", java.util.Locale.getDefault())
                            	"Due: ${sdf.format(java.util.Date(deadline!!))}"
                            } else "No Deadline"
                            Text(dateText, fontSize = 10.sp)
                        }

                        if (showDatePicker) {
                            val context = androidx.compose.ui.platform.LocalContext.current
                            val calendar = java.util.Calendar.getInstance()
                            android.app.DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    calendar.set(year, month, dayOfMonth)
                                    deadline = calendar.timeInMillis
                                    showDatePicker = false
                                },
                                calendar.get(java.util.Calendar.YEAR),
                                calendar.get(java.util.Calendar.MONTH),
                                calendar.get(java.util.Calendar.DAY_OF_MONTH)
                            ).apply {
                                setOnCancelListener { showDatePicker = false }
                            }.show()
                        }

                        Spacer(Modifier.weight(1f))
                        Button(
                            onClick = { 
                                if(newTaskName.isNotBlank()){
                                    viewModel.addTask(newTaskName, newTaskUrgent, deadline)
                                    newTaskName = ""
                                    newTaskUrgent = false
                                    deadline = null
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSurface)
                        ) {
                            Text("Add Task")
                        }
                    }
                }
             }

        // List tasks
        val filteredTasks = if (isOverwhelmed) tasks.filter { it.isUrgent } else tasks
        if (filteredTasks.isEmpty()) {
            Box(Modifier.weight(1f).fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                Text("Your task list is empty. Take a deep breath.", color = MaterialTheme.colorScheme.onBackground)
            }
        } else {
            androidx.compose.foundation.lazy.LazyColumn(modifier = Modifier.weight(1f).fillMaxWidth()) {
                items(filteredTasks.size) { index ->
                    val task = filteredTasks[index]
                    TaskItem(task, onToggle = { viewModel.toggleTask(task.taskId, it) })
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: TaskEntity, onToggle: (Boolean) -> Unit) {
    Card(
        shape = androidx.compose.foundation.shape.RoundedCornerShape(32.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(1.dp, com.example.ui.theme.BorderLight, androidx.compose.foundation.shape.RoundedCornerShape(32.dp)),
        colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.CardGreen)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Surface(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                ) {
                    Text(
                        if (task.isCompleted) "COMPLETED" else "ACTIVE FOCUS",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                if (task.isUrgent) {
                    Text(
                        "URGENT",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = task.isCompleted, onCheckedChange = onToggle, modifier = Modifier.testTag("task_checkbox"))
                Spacer(Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(task.taskName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    val infoText = if (task.deadline != null) {
                        val sdf = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault())
                        "Due ${sdf.format(java.util.Date(task.deadline))}"
                    } else "No Deadline"
                    Text(infoText, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                }
            }
            
            if (task.deadline != null && !task.isCompleted) {
                Spacer(Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = { /* Handle Reschedule */ }) {
                        Text("Missed it? Life happens. Reschedule.", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.height(6.dp).weight(1f).background(MaterialTheme.colorScheme.onSurface, androidx.compose.foundation.shape.CircleShape))
                Box(modifier = Modifier.height(6.dp).weight(1f).background(MaterialTheme.colorScheme.onSurface, androidx.compose.foundation.shape.CircleShape))
                Box(modifier = Modifier.height(6.dp).weight(1f).background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), androidx.compose.foundation.shape.CircleShape))
                Box(modifier = Modifier.height(6.dp).weight(1f).background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), androidx.compose.foundation.shape.CircleShape))
                Box(modifier = Modifier.height(6.dp).weight(1f).background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), androidx.compose.foundation.shape.CircleShape))
            }
        }
    }
}
