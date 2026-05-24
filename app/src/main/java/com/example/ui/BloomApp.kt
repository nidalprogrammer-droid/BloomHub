package com.example.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.border
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.*

@Composable
fun BloomApp(viewModel: AppViewModel) {
    val navController = rememberNavController()
    val user by viewModel.currentUser.collectAsStateWithLifecycle()
    
    // Check if we need to show onboarding, we fetch user once here
    // Wait for the user query to initialize
    var isInitialized by remember { mutableStateOf(false) }
    LaunchedEffect(user) {
        if (!isInitialized) isInitialized = true
    }
    
    // Load user state first so we only render NavHost once the decision is made.
    if (!isInitialized) {
        return // Loading screen
    }

    Scaffold(
        bottomBar = {
            if (user != null) {
                BloomBottomNav(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (user == null) OnboardingRoute else DashboardRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<OnboardingRoute> {
                OnboardingScreen(
                    onUserCreated = { name ->
                        viewModel.createUser(name)
                        navController.navigate(DashboardRoute) {
                            popUpTo(OnboardingRoute) { inclusive = true }
                        }
                    }
                )
            }
            composable<DashboardRoute> {
                DashboardScreen(viewModel)
            }
            composable<WellbeingRoute> {
                WellbeingScreen(viewModel)
            }
            composable<ZenGardenRoute> {
                ZenGardenScreen(viewModel)
            }
        }
    }
}

@Composable
fun BloomBottomNav(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp,
        modifier = Modifier.border(1.dp, com.example.ui.theme.BorderLight)
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
            label = { Text("Tasks", fontSize = 10.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold) },
            selected = currentRoute?.contains("DashboardRoute") == true,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.onSurface,
                selectedIconColor = MaterialTheme.colorScheme.surface,
                unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                selectedTextColor = MaterialTheme.colorScheme.onSurface,
                unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            ),
            onClick = {
                navController.navigate(DashboardRoute) {
                    popUpTo(DashboardRoute) { inclusive = true }
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Wellbeing") },
            label = { Text("Wellbeing", fontSize = 10.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold) },
            selected = currentRoute?.contains("WellbeingRoute") == true,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.onSurface,
                selectedIconColor = MaterialTheme.colorScheme.surface,
                unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                selectedTextColor = MaterialTheme.colorScheme.onSurface,
                unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            ),
            onClick = {
                navController.navigate(WellbeingRoute) {
                    popUpTo(DashboardRoute) 
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Star, contentDescription = "Zen Garden") },
            label = { Text("Social", fontSize = 10.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold) },
            selected = currentRoute?.contains("ZenGardenRoute") == true,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.onSurface,
                selectedIconColor = MaterialTheme.colorScheme.surface,
                unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                selectedTextColor = MaterialTheme.colorScheme.onSurface,
                unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            ),
            onClick = {
                navController.navigate(ZenGardenRoute) {
                    popUpTo(DashboardRoute) 
                }
            }
        )
    }
}
