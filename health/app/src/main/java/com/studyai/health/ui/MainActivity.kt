package com.studyai.health.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.studyai.health.ui.components.BottomNavigationBar
import com.studyai.health.ui.screens.HeartRateScreen
import com.studyai.health.ui.screens.HomeScreen
import com.studyai.health.ui.screens.spo2.SpO2MeasurementScreen
import com.studyai.health.ui.theme.StudyAIHealthTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity for the application
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyAIHealthTheme {
                MainScreen()
            }
        }
    }
}

/**
 * Main screen containing the app navigation
 */
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = currentRoute
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            AppNavHost(navController = navController)
        }
    }
}

/**
 * Navigation routes for the app
 */
sealed class AppScreen(val route: String) {
    object Home : AppScreen("home")
    object HeartRate : AppScreen("heart_rate")
    object SpO2 : AppScreen("spo2_measurement")
    object Stress : AppScreen("stress")
    object History : AppScreen("history")
    object AiAdvisor : AppScreen("ai_advisor")
    object Schedule : AppScreen("schedule")
    object Settings : AppScreen("settings")
}

/**
 * Navigation host for the app
 */
@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Home.route
    ) {
        composable(AppScreen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(AppScreen.HeartRate.route) {
            HeartRateScreen(navController = navController)
        }
        composable(AppScreen.SpO2.route) {
            SpO2MeasurementScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        // Other screen composables will be added as they are implemented
    }
} 