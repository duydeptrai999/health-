package com.studyai.health.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.studyai.health.ui.theme.AppIcons
import com.studyai.health.ui.theme.CardShape
import com.studyai.health.ui.theme.Heart
import com.studyai.health.ui.theme.SpaceMedium
import com.studyai.health.ui.theme.SpaceSmall
import com.studyai.health.ui.theme.StudyAIHealthTheme
import com.studyai.health.ui.theme.dimens

/**
 * A card component for displaying health metrics.
 *
 * @param title The title of the metric
 * @param value The current value of the metric
 * @param unit The unit of measurement (e.g., "bpm", "steps")
 * @param icon The icon representing the metric
 * @param color The accent color for the metric
 * @param onClick Callback when the card is clicked
 * @param modifier Modifier to be applied to the card
 * @param trendValue Optional trend value to display (e.g., "+5%" or "-2%")
 * @param trendColor Optional color for the trend value
 */
@Composable
fun MetricCard(
    title: String,
    value: String,
    unit: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    trendValue: String? = null,
    trendColor: Color = MaterialTheme.colorScheme.onSurface
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimens.contentPaddingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon with background
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = color.copy(alpha = 0.2f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = color,
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
                
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = value,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Spacer(modifier = Modifier.width(SpaceSmall))
                    
                    Text(
                        text = unit,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    
                    trendValue?.let {
                        Spacer(modifier = Modifier.width(SpaceMedium))
                        
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = trendColor,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }
            }
            
            Icon(
                imageVector = AppIcons.Forward,
                contentDescription = "View details",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MetricCardPreview() {
    StudyAIHealthTheme {
        MetricCard(
            title = "Heart Rate",
            value = "72",
            unit = "bpm",
            icon = AppIcons.Heart,
            color = Heart,
            onClick = {},
            trendValue = "+3%",
            trendColor = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MetricCardNoTrendPreview() {
    StudyAIHealthTheme {
        MetricCard(
            title = "Steps",
            value = "8,543",
            unit = "steps",
            icon = AppIcons.HomeFilled,
            color = MaterialTheme.colorScheme.primary,
            onClick = {}
        )
    }
} 