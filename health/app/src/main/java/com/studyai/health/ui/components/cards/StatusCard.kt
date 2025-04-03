package com.studyai.health.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.studyai.health.ui.theme.AppIcons
import com.studyai.health.ui.theme.HealthBad
import com.studyai.health.ui.theme.HealthExcellent
import com.studyai.health.ui.theme.HealthGood
import com.studyai.health.ui.theme.HealthPoor
import com.studyai.health.ui.theme.HealthAverage

/**
 * Enum class representing different health status levels.
 */
enum class HealthStatus(val label: String, val color: Color, val icon: Any) {
    EXCELLENT("Excellent", HealthExcellent, AppIcons.ThumbUp),
    GOOD("Good", HealthGood, AppIcons.ThumbUp),
    AVERAGE("Average", HealthAverage, AppIcons.Info),
    POOR("Poor", HealthPoor, AppIcons.Warning),
    BAD("Bad", HealthBad, AppIcons.WarningRounded),
}

/**
 * A card that displays a health status with a colored indicator, title, and description.
 *
 * @param status The health status to display
 * @param title Optional title to override the status label
 * @param description Additional description text
 * @param value Optional value to display (e.g., "95%", "120/80")
 * @param modifier Modifier for styling
 */
@Composable
fun StatusCard(
    status: HealthStatus,
    title: String? = null,
    description: String,
    value: String? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Status Icon with colored background
            Icon(
                imageVector = status.icon as androidx.compose.ui.graphics.vector.ImageVector,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = status.color.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(8.dp),
                tint = status.color
            )
            
            // Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Title
                Text(
                    text = title ?: status.label,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = status.color
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Description
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }
            
            // Optional Value
            value?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = status.color
                )
            }
        }
    }
} 