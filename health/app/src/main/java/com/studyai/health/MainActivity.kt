package com.studyai.health

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.studyai.health.ui.screens.HomeScreen
import com.studyai.health.ui.screens.spo2.SpO2MeasurementScreen
import com.studyai.health.ui.theme.StudyAIHealthTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main routes for app navigation
 */
object Route {
    const val HOME = "home"
    const val HEART_RATE = "heart_rate"
    const val SPO2_MEASUREMENT = "spo2_measurement"
    const val STRESS = "stress"
    const val HISTORY = "history"
    const val SETTINGS = "settings"
}

/**
 * App screens for navigation
 */
sealed class AppScreen(val route: String) {
    object Home : AppScreen(Route.HOME)
    object HeartRate : AppScreen(Route.HEART_RATE)
    object SpO2 : AppScreen(Route.SPO2_MEASUREMENT)
    object Stress : AppScreen(Route.STRESS)
    object History : AppScreen(Route.HISTORY)
    object Settings : AppScreen(Route.SETTINGS)
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyAIHealthTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    Scaffold { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Route.HOME,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Route.HOME) {
                HomeScreen(navController = navController)
            }
            
            composable(Route.SPO2_MEASUREMENT) {
                SpO2MeasurementScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
            
            // Other screen composables here (HeartRate, Stress, History, Settings)
        }
    }
} 