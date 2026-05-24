package com.example.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.PastelBackground
import com.example.ui.theme.SolidBlack
import com.example.ui.theme.SolidWhite

class AccountabilityOverlayActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PastelBackground,
                    contentColor = SolidBlack
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "DISTRACTION DETECTED",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 2.sp,
                            color = SolidBlack.copy(alpha = 0.5f)
                        )
                        Spacer(Modifier.height(24.dp))
                        Text(
                            "Your daily study milestone is incomplete.",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            lineHeight = 40.sp,
                            color = SolidBlack
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            "Redirect your focus to your academic goals before allowing distractions.",
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            color = SolidBlack.copy(alpha = 0.7f)
                        )
                        
                        Spacer(Modifier.height(48.dp))
                        
                        Button(
                            onClick = { finish() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SolidBlack,
                                contentColor = SolidWhite
                            ),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(32.dp)
                        ) {
                            Text("COMPLETE MILESTONE", fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                        }
                        
                        Spacer(Modifier.height(16.dp))
                        
                        OutlinedButton(
                            onClick = { finish() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = SolidBlack
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SolidBlack),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(32.dp)
                        ) {
                            Text("DISTRACT", fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                        }
                    }
                }
            }
        }
    }
}
