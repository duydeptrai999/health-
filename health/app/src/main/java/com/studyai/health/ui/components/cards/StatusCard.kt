package com.studyai.health.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.studyai.health.ui.theme.AppIcons
import com.studyai.health.ui.theme.CardShape
import com.studyai.health.ui.theme.HealthExcellent
import com.studyai.health.ui.theme.HealthGood
import com.studyai.health.ui.theme.Primary
import com.studyai.health.ui.theme.SpaceMedium
import com.studyai.health.ui.theme.SpaceSmall
import com.studyai.health.ui.theme.StudyAIHealthTheme
import com.studyai.health.ui.theme.dimens

/**
 * Status level enum to represent different health statuses
 */
enum class HealthStatus(val color: Color, val label: String) {
    EXCELLENT(HealthExcellent, "Excellent"),
    GOOD(HealthGood, "Good"),
    AVERAGE(Color(0xFFFFD600), "Average"),
    POOR(Color(0xFFFF9100), "Poor"),
    BAD(Color(0xFFFF3D00), "Bad")
}

/**
 * A card component for displaying health status with a progress indicator.
 *
 * @param title The title of the status
 * @param description A brief description of the status
 * @param status The health status level
 * @param progress The progress value (between 0.0 and 1.0)
 * @param icon The icon representing the status
 * @param onClick Callback when the card is clicked
 * @param modifier Modifier to be applied to the card
 */
@Composable
fun StatusCard(
    title: String,
    description: String,
    status: HealthStatus,
    progress: Float,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = CardShape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = MaterialTheme.dimens.cardElevation
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.contentPaddingMedium)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon with background
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = status.color.copy(alpha = 0.2f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = status.color,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(MaterialTheme.dimens.spaceMedium))
                
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Spacer(modifier = Modifier.height(SpaceSmall))
                    
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                Spacer(modifier = Modifier.width(SpaceMedium))
                
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(status.color.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${(progress * 100).toInt()}",
                        style = MaterialTheme.typography.bodySmall,
                        color = status.color
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .weight(1f)
                        .height(8.dp)
                        .clip(MaterialTheme.shapes.small),
                    color = status.color,
                    trackColor = status.color.copy(alpha = 0.1f),
                    strokeCap = StrokeCap.Round
                )
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Text(
                    text = status.label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = status.color
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatusCardPreview() {
    StudyAIHealthTheme {
        StatusCard(
            title = "Overall Health",
            description = "Your health status is good based on recent measurements",
            status = HealthStatus.GOOD,
            progress = 0.75f,
            icon = AppIcons.Heart,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StatusCardExcellentPreview() {
    StudyAIHealthTheme {
        StatusCard(
            title = "Activity Level",
            description = "You're exceeding your activity goals",
            status = HealthStatus.EXCELLENT,
            progress = 0.92f,
            icon = AppIcons.FavoriteFilled,
            onClick = {}
        )
    }
} 