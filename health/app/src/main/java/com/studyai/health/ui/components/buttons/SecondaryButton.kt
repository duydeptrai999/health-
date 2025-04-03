package com.studyai.health.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.studyai.health.ui.theme.ButtonHeight
import com.studyai.health.ui.theme.ButtonIconSize
import com.studyai.health.ui.theme.PillShape
import com.studyai.health.ui.theme.SpaceMedium
import com.studyai.health.ui.theme.StudyAIHealthTheme

/**
 * Secondary button component for secondary actions in the app
 *
 * @param text Text to display in the button
 * @param onClick Callback when button is clicked
 * @param modifier Modifier to be applied to the button
 * @param enabled Whether the button is enabled or not
 * @param isLoading Show loading indicator instead of text
 * @param icon Optional icon to display before the text
 * @param contentColor Text and icon color
 * @param borderColor Border color of the button
 */
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    icon: ImageVector? = null,
    contentColor: Color = MaterialTheme.colorScheme.primary,
    borderColor: Color = MaterialTheme.colorScheme.primary
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .defaultMinSize(minHeight = ButtonHeight),
        enabled = enabled && !isLoading,
        shape = PillShape,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = contentColor,
            disabledContentColor = contentColor.copy(alpha = 0.5f)
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (enabled) borderColor else borderColor.copy(alpha = 0.5f)
        ),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = contentColor,
                strokeWidth = 2.dp
            )
        } else {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.size(ButtonIconSize)
                )
                Spacer(modifier = Modifier.width(SpaceMedium))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondaryButtonPreview() {
    StudyAIHealthTheme {
        SecondaryButton(
            text = "Secondary Button",
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SecondaryButtonLoadingPreview() {
    StudyAIHealthTheme {
        SecondaryButton(
            text = "Secondary Button",
            onClick = {},
            isLoading = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SecondaryButtonDisabledPreview() {
    StudyAIHealthTheme {
        SecondaryButton(
            text = "Secondary Button",
            onClick = {},
            enabled = false
        )
    }
} 