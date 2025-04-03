package com.studyai.health.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.studyai.health.R
import com.studyai.health.ui.AppScreen
import com.studyai.health.ui.components.HealthCard

/**
 * Home screen of the app
 */
@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        HomeHeader()
        Spacer(modifier = Modifier.height(16.dp))
        QuickActionButtons(navController)
        Spacer(modifier = Modifier.height(24.dp))
        LatestMeasurements()
        Spacer(modifier = Modifier.height(24.dp))
        DailyRecommendations()
    }
}

@Composable
fun HomeHeader() {
    Column {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Theo dõi sức khỏe mỗi ngày",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun QuickActionButtons(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ActionButton(
            iconResId = R.drawable.ic_heart_rate,
            textResId = R.string.nav_heart_rate,
            onClick = { navController.navigate(AppScreen.HeartRate.route) },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        ActionButton(
            iconResId = R.drawable.ic_spo2,
            textResId = R.string.nav_spo2,
            onClick = { navController.navigate(AppScreen.SpO2.route) },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        ActionButton(
            iconResId = R.drawable.ic_stress,
            textResId = R.string.nav_stress,
            onClick = { navController.navigate(AppScreen.Stress.route) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ActionButton(
    iconResId: Int,
    textResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier.height(100.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = textResId),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun LatestMeasurements() {
    Column {
        Text(
            text = "Chỉ số gần đây",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        HealthCard(
            title = "Nhịp tim",
            value = "72",
            unit = "BPM",
            iconResId = R.drawable.ic_heart_rate,
            color = MaterialTheme.colorScheme.primary,
            timestamp = "10 phút trước",
            onClick = {}
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        HealthCard(
            title = "Nồng độ oxy",
            value = "98",
            unit = "%",
            iconResId = R.drawable.ic_spo2,
            color = MaterialTheme.colorScheme.tertiary,
            timestamp = "30 phút trước",
            onClick = {}
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        HealthCard(
            title = "Mức độ căng thẳng",
            value = "Thấp",
            iconResId = R.drawable.ic_stress,
            color = MaterialTheme.colorScheme.secondary,
            timestamp = "1 giờ trước",
            onClick = {}
        )
    }
}

@Composable
fun DailyRecommendations() {
    Column {
        Text(
            text = "Lời khuyên hôm nay",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Uống đủ nước",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Bạn nên uống ít nhất 2 lít nước mỗi ngày để duy trì sức khỏe tốt.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Tập thể dục nhẹ nhàng",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Dành 30 phút mỗi ngày để đi bộ hoặc tập thể dục nhẹ nhàng.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                FilledTonalButton(
                    onClick = { /* TODO: Navigate to AI Advisor */ },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Xem thêm")
                }
            }
        }
    }
} 