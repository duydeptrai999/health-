# UI Components

This directory contains reusable UI components for the StudyAI Health application. These components follow the Material 3 design guidelines and use the theme defined in `com.studyai.health.ui.theme`.

## Structure

The UI components are organized by functionality:

### Buttons
- Standard buttons with customizable styles
- Icon buttons
- Toggle buttons

### Cards
- Health status cards
- Metric cards
- Summary cards

### Charts
- Line charts for health data trends
- Bar charts for activity comparisons
- Stat displays

### Dialogs
- Alert dialogs
- Confirmation dialogs
- Input dialogs

### Inputs
- Text fields
- Selection controls
- Sliders and range inputs

### Layout
- Scaffold templates
- Section headers
- Dividers and spacers

### Navigation
- Bottom navigation bar
- Navigation drawer components
- App bars

### Status
- Progress indicators
- Status banners
- Empty states

## Usage Example

```kotlin
@Composable
fun HealthMetricCard(
    title: String,
    value: String,
    unit: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.spaceSmall)
            .clickable { onClick() },
        shape = CardShape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = MaterialTheme.dimens.cardElevation
        )
    ) {
        Row(
            modifier = Modifier
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
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = value,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = unit,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
            
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "View details",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
```

## Component Guidelines

1. All components should use the app theme for styling
2. Components should be responsive and adapt to different screen sizes
3. Use Material 3 components and guidelines where possible
4. Provide proper accessibility support (content descriptions, etc.)
5. Keep components focused on a single responsibility
6. Maintain consistent naming conventions
7. Use Composition Local values for theme access 