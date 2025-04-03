package com.studyai.health.ui.components.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.studyai.health.ui.theme.AppIcons
import com.studyai.health.ui.theme.CardShape
import com.studyai.health.ui.theme.SpaceMedium
import com.studyai.health.ui.theme.SpaceSmall
import com.studyai.health.ui.theme.StudyAIHealthTheme
import com.studyai.health.ui.theme.dimens

/**
 * Data class representing a summary item to be displayed in the summary card
 *
 * @param title Title of the item
 * @param value Value of the item
 * @param icon Optional icon for the item
 */
data class SummaryItem(
    val title: String,
    val value: String,
    val icon: ImageVector? = null
)

/**
 * A card component for displaying a summary of health data
 *
 * @param title Title of the summary card
 * @param items List of summary items to display
 * @param onClick Callback when the card is clicked
 * @param modifier Modifier to be applied to the card
 * @param showDividers Whether to show dividers between items
 */
@Composable
fun SummaryCard(
    title: String,
    items: List<SummaryItem>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    showDividers: Boolean = true
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
            // Card title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.weight(1f))
                
                Icon(
                    imageVector = AppIcons.Forward,
                    contentDescription = "View details",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceMedium))
            
            // Items
            items.forEachIndexed { index, item ->
                if (index > 0 && showDividers) {
                    Divider(
                        color = MaterialTheme.colorScheme.outlineVariant,
                        modifier = Modifier.padding(vertical = SpaceSmall)
                    )
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item.icon?.let { 
                        Icon(
                            imageVector = it,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(end = SpaceSmall)
                        )
                        Spacer(modifier = Modifier.width(SpaceSmall))
                    }
                    
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    Text(
                        text = item.value,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SummaryCardPreview() {
    StudyAIHealthTheme {
        SummaryCard(
            title = "Daily Summary",
            items = listOf(
                SummaryItem("Steps", "8,543", AppIcons.HomeFilled),
                SummaryItem("Distance", "4.2 km", AppIcons.Location),
                SummaryItem("Calories", "367 kcal", AppIcons.FavoriteFilled),
                SummaryItem("Active Time", "48 min", AppIcons.Calendar)
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SummaryCardNoDividersPreview() {
    StudyAIHealthTheme {
        SummaryCard(
            title = "Weekly Summary",
            items = listOf(
                SummaryItem("Average Steps", "7,842"),
                SummaryItem("Average Sleep", "6.8 hr"),
                SummaryItem("Active Days", "5/7")
            ),
            onClick = {},
            showDividers = false
        )
    }
} 