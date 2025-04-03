package com.studyai.health.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.studyai.health.R
import com.studyai.health.ui.AppScreen

/**
 * Bottom navigation bar for the app
 */
@Composable
fun BottomNavigationBar(
    navController: NavController,
    currentRoute: String?
) {
    val items = listOf(
        NavigationItem(
            route = AppScreen.Home.route,
            titleResId = R.string.nav_home,
            iconResId = R.drawable.ic_home
        ),
        NavigationItem(
            route = AppScreen.HeartRate.route,
            titleResId = R.string.nav_heart_rate,
            iconResId = R.drawable.ic_heart_rate
        ),
        NavigationItem(
            route = AppScreen.SpO2.route,
            titleResId = R.string.nav_spo2,
            iconResId = R.drawable.ic_spo2
        ),
        NavigationItem(
            route = AppScreen.History.route,
            titleResId = R.string.nav_history,
            iconResId = R.drawable.ic_history
        ),
        NavigationItem(
            route = AppScreen.AiAdvisor.route,
            titleResId = R.string.nav_ai_advisor,
            iconResId = R.drawable.ic_ai_advisor
        )
    )
    
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconResId),
                        contentDescription = stringResource(id = item.titleResId)
                    )
                },
                label = { Text(text = stringResource(id = item.titleResId)) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

/**
 * Data class representing an item in the bottom navigation bar
 */
data class NavigationItem(
    val route: String,
    val titleResId: Int,
    val iconResId: Int
) 