package com.studyai.health.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.studyai.health.ui.theme.AppIcons
import com.studyai.health.ui.theme.IconSizeMedium
import com.studyai.health.ui.theme.StudyAIHealthTheme

/**
 * Primary icon button with filled background
 *
 * @param icon Icon to display
 * @param onClick Callback when button is clicked
 * @param modifier Modifier to be applied to the button
 * @param contentDescription Accessibility description for the icon
 * @param enabled Whether the button is enabled
 * @param iconTint Tint color for the icon
 * @param containerColor Background color of the button
 */
@Composable
fun PrimaryIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    enabled: Boolean = true,
    iconTint: Color = MaterialTheme.colorScheme.onPrimary,
    containerColor: Color = MaterialTheme.colorScheme.primary
) {
    FilledIconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = containerColor,
            contentColor = iconTint,
            disabledContainerColor = containerColor.copy(alpha = 0.5f),
            disabledContentColor = iconTint.copy(alpha = 0.5f)
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(IconSizeMedium)
        )
    }
}

/**
 * Secondary icon button with outlined style
 *
 * @param icon Icon to display
 * @param onClick Callback when button is clicked
 * @param modifier Modifier to be applied to the button
 * @param contentDescription Accessibility description for the icon
 * @param enabled Whether the button is enabled
 * @param iconTint Tint color for the icon
 * @param borderColor Border color of the button
 */
@Composable
fun SecondaryIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    enabled: Boolean = true,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    borderColor: Color = MaterialTheme.colorScheme.primary
) {
    OutlinedIconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = IconButtonDefaults.outlinedIconButtonColors(
            contentColor = iconTint,
            disabledContentColor = iconTint.copy(alpha = 0.5f)
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = if (enabled) borderColor else borderColor.copy(alpha = 0.5f)
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(IconSizeMedium)
        )
    }
}

/**
 * Floating action button style icon button
 *
 * @param icon Icon to display
 * @param onClick Callback when button is clicked
 * @param modifier Modifier to be applied to the button
 * @param contentDescription Accessibility description for the icon
 * @param iconTint Tint color for the icon
 * @param backgroundColor Background color of the button
 */
@Composable
fun FloatingIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    iconTint: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer
) {
    Surface(
        modifier = modifier.size(56.dp),
        onClick = onClick,
        shape = CircleShape,
        color = backgroundColor,
        shadowElevation = 6.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(24.dp),
                tint = iconTint
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryIconButtonPreview() {
    StudyAIHealthTheme {
        PrimaryIconButton(
            icon = AppIcons.Add,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SecondaryIconButtonPreview() {
    StudyAIHealthTheme {
        SecondaryIconButton(
            icon = AppIcons.Add,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FloatingIconButtonPreview() {
    StudyAIHealthTheme {
        FloatingIconButton(
            icon = AppIcons.Add,
            onClick = {}
        )
    }
}