package com.example.ui

import android.os.VibrationEffect
import android.os.Vibrator
import android.os.Build
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState

@Composable
fun WellbeingScreen(viewModel: AppViewModel) {
    val user by viewModel.currentUser.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var worryText by remember { mutableStateOf("") }
    var showWorryBox by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(scrollState)) {
        Text("WELLBEING CENTER", fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f), modifier = Modifier.padding(horizontal = 8.dp))
        Spacer(Modifier.height(24.dp))

        // Study Strike
        Card(
            shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, com.example.ui.theme.BorderLight, androidx.compose.foundation.shape.RoundedCornerShape(24.dp)),
            colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.CardBlue)
        ) {
            Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Grace Days Left", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text("Study Strike Protection", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                }
                androidx.compose.material3.Surface(
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                ) {
                    Text("${user?.graceDaysLeft ?: 0}", modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.surface)
                }
            }
        }
        Spacer(Modifier.height(16.dp))

        // Focus Timer & Audio
        Card(
            shape = androidx.compose.foundation.shape.RoundedCornerShape(32.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, com.example.ui.theme.BorderLight, androidx.compose.foundation.shape.RoundedCornerShape(32.dp)),
            colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.CardGray)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("FOCUS & AUDIO", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text("Offline audio loops and mindful pomodoro", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f), lineHeight = 14.sp)
                Spacer(Modifier.height(16.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(
                        onClick = { /* Start 25m Timer */ },
                        colors = ButtonDefaults.buttonColors(containerColor = com.example.ui.theme.SolidBlack, contentColor = com.example.ui.theme.SolidWhite)
                    ) {
                        Text("25m Focus Block", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = { /* Play Audio */ },
                        colors = ButtonDefaults.buttonColors(containerColor = com.example.ui.theme.SolidBlack, contentColor = com.example.ui.theme.SolidWhite)
                    ) {
                         Text("Play Ambient Rain", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        
        Spacer(Modifier.height(16.dp))

        // Worry Box
        AnimatedVisibility(
            visible = showWorryBox,
            exit = fadeOut(animationSpec = tween(1500))
        ) {
            Card(
                shape = androidx.compose.foundation.shape.RoundedCornerShape(32.dp),
                modifier = Modifier.fillMaxWidth().border(1.dp, com.example.ui.theme.BorderLight, androidx.compose.foundation.shape.RoundedCornerShape(32.dp)),
                colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.CardPink)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Worry Box", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text("Tap to dissolve anxieties into the wind.", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f), lineHeight = 14.sp)
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = worryText,
                        onValueChange = { worryText = it },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = com.example.ui.theme.SolidWhite,
                            unfocusedContainerColor = com.example.ui.theme.SolidWhite
                        )
                    )
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                showWorryBox = false
                                delay(2000)
                                worryText = ""
                                showWorryBox = true // allow adding again
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(40.dp),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = com.example.ui.theme.SolidBlack, contentColor = com.example.ui.theme.SolidWhite)
                    ) {
                        Text("RELEASE", fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    }
                }
            }
        }
        
        if (!showWorryBox) {
             Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                 Text("Your worries have dissolved into the ether.", fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, fontSize = 12.sp)
             }
        }

        Spacer(Modifier.height(16.dp))

        // SOS Calm
        Card(
            shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, com.example.ui.theme.BorderLight, androidx.compose.foundation.shape.RoundedCornerShape(24.dp)),
            colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.CardOrange)
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(32.dp).background(com.example.ui.theme.SolidBlack, CircleShape), contentAlignment = Alignment.Center) {
                    Box(modifier = Modifier.size(12.dp).border(2.dp, com.example.ui.theme.SolidWhite, androidx.compose.foundation.shape.RoundedCornerShape(2.dp)))
                }
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("SOS Calm", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text("1-min Box Breathing", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                }
                Button(
                    onClick = {
                        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE))
                        } else {
                            @Suppress("DEPRECATION")
                            vibrator.vibrate(1000)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = com.example.ui.theme.SolidWhite, contentColor = com.example.ui.theme.SolidBlack),
                    border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.SolidBlack),
                    modifier = Modifier.height(40.dp)
                ) {
                    Text("START", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Encouragement Leaves & Voices
        Card(
            shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, com.example.ui.theme.BorderLight, androidx.compose.foundation.shape.RoundedCornerShape(24.dp)),
            colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.CardGreen)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("ENCOURAGEMENT LEAVES", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text("Write an anonymous note, or voice-to-text micro-journal.", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                Spacer(Modifier.height(12.dp))
                var isRecording by remember { mutableStateOf(false) }
                val launcher = androidx.activity.compose.rememberLauncherForActivityResult(
                    contract = androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
                ) { result ->
                    isRecording = false
                    if (result.resultCode == android.app.Activity.RESULT_OK) {
                        val data = result.data?.getStringArrayListExtra(android.speech.RecognizerIntent.EXTRA_RESULTS)
                        data?.get(0)?.let { transcript ->
                            viewModel.addJournal(transcript)
                        }
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    OutlinedButton(
                        onClick = { /* Read Note */ },
                        border = androidx.compose.foundation.BorderStroke(1.dp, com.example.ui.theme.SolidBlack),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = com.example.ui.theme.SolidBlack)
                    ) {
                        Text("PULL A LEAF", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = {
                            isRecording = true
                            val intent = android.content.Intent(android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL, android.speech.RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                            }
                            try { launcher.launch(intent) } catch (e: Exception) { isRecording = false }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isRecording) com.example.ui.theme.CardPink else com.example.ui.theme.SolidBlack,
                            contentColor = if (isRecording) com.example.ui.theme.SolidBlack else com.example.ui.theme.SolidWhite
                        )
                    ) {
                        Text(if (isRecording) "LISTENING..." else "VOICE JOURNAL", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun ZenGardenScreen(viewModel: AppViewModel) {
    val user by viewModel.currentUser.collectAsStateWithLifecycle()
    var friendId by remember { mutableStateOf("") }
    
    val scrollState = rememberScrollState()
    
    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(scrollState)) {
        Text("SOCIAL MODULE", fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f), modifier = Modifier.padding(horizontal = 8.dp))
        Spacer(Modifier.height(24.dp))
        
        Card(
            shape = androidx.compose.foundation.shape.RoundedCornerShape(32.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, com.example.ui.theme.BorderLight, androidx.compose.foundation.shape.RoundedCornerShape(32.dp)),
            colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.CardBlue)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier.size(80.dp).background(com.example.ui.theme.SolidWhite, CircleShape).border(1.dp, com.example.ui.theme.BorderLight, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🌱", fontSize = 32.sp)
                }
                Spacer(Modifier.height(16.dp))
                Text("Shared Garden", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = com.example.ui.theme.SolidBlack)
                Text("With [Friend Name]", fontSize = 12.sp, color = com.example.ui.theme.SolidBlack.copy(alpha = 0.6f), modifier = Modifier.padding(top = 4.dp))
                
                Spacer(Modifier.height(32.dp))
                Button(
                    onClick = { /* Sync Garden */ },
                    colors = ButtonDefaults.buttonColors(containerColor = com.example.ui.theme.SolidBlack, contentColor = com.example.ui.theme.SolidWhite)
                ) {
                    Text("Refresh Sync", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        
        Spacer(Modifier.height(16.dp))

        Card(
            shape = androidx.compose.foundation.shape.RoundedCornerShape(32.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, com.example.ui.theme.BorderLight, androidx.compose.foundation.shape.RoundedCornerShape(32.dp)),
            colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.CardGreen)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("THE UNPLUGGED REWARD", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = com.example.ui.theme.SolidBlack.copy(alpha = 0.6f))
                Spacer(Modifier.height(16.dp))
                
                val context = androidx.compose.ui.platform.LocalContext.current
                var isOffline by remember { mutableStateOf(false) }
                
                LaunchedEffect(Unit) {
                    try {
                        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
                        val activeNetwork = cm.activeNetwork
                        val capabilities = cm.getNetworkCapabilities(activeNetwork)
                        isOffline = capabilities == null || 
                            (!capabilities.hasTransport(android.net.NetworkCapabilities.TRANSPORT_WIFI) && 
                             !capabilities.hasTransport(android.net.NetworkCapabilities.TRANSPORT_CELLULAR))
                    } catch (e: SecurityException) {
                        isOffline = false
                    }
                }

                if (isOffline) {
                    Text("🌸", fontSize = 48.sp)
                    Spacer(Modifier.height(12.dp))
                    Text("Rare Pastel Lotus Unlocked", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text("Reward for completely unplugging.", fontSize = 12.sp, color = com.example.ui.theme.SolidBlack.copy(alpha = 0.7f), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                } else {
                    Text("Turn off Wi-Fi & Data while studying for a rare flower.", fontSize = 12.sp, color = com.example.ui.theme.SolidBlack.copy(alpha = 0.7f), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = {
                            try {
                                val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
                                val an = cm.activeNetwork
                                val cap = cm.getNetworkCapabilities(an)
                                isOffline = cap == null
                            } catch (e: SecurityException) {
                                isOffline = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = com.example.ui.theme.SolidBlack)
                    ) {
                        Text("Check Connection")
                    }
                }
            }
        }
        
        Spacer(Modifier.height(16.dp))
        
        Card(
            shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, com.example.ui.theme.BorderLight, androidx.compose.foundation.shape.RoundedCornerShape(24.dp)),
            colors = CardDefaults.cardColors(containerColor = com.example.ui.theme.CardGray)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("CONNECT PRIVATELY", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text("Share your ID or enter a friend's ID to study together.", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                Spacer(Modifier.height(16.dp))
                Text("Your ID: ${user?.userId ?: "Generating..."}", fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Spacer(Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = friendId,
                        onValueChange = { friendId = it },
                        modifier = Modifier.weight(1f),
                        label = { Text("Friend ID", fontSize = 10.sp) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = com.example.ui.theme.SolidWhite,
                            unfocusedContainerColor = com.example.ui.theme.SolidWhite
                        )
                    )
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = { /* Link ID */ },
                        colors = ButtonDefaults.buttonColors(containerColor = com.example.ui.theme.SolidBlack, contentColor = com.example.ui.theme.SolidWhite)
                    ) {
                        Text("LINK")
                    }
                }
            }
        }
    }
}
