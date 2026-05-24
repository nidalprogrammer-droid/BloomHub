package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.data.AppDatabase
import com.example.data.AppRepository
import com.example.ui.*
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = AppDatabase.getDatabase(this)
        val repository = AppRepository(db)
        val factory = AppViewModelFactory(repository)

        setContent {
            val viewModel: AppViewModel = viewModel(factory = factory)
            val isSunset = viewModel.isSunsetModeActive()

            MyApplicationTheme(isSunset = isSunset) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BloomApp(viewModel)
                }
            }
        }
    }
}
