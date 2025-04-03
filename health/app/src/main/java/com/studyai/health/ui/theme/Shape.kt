package com.studyai.health.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Shape definitions for the application
val Shapes = Shapes(
    // Small components like chips, small buttons
    small = RoundedCornerShape(4.dp),
    
    // Medium components like cards, dialogs
    medium = RoundedCornerShape(8.dp),
    
    // Large components like bottom sheets, modals
    large = RoundedCornerShape(16.dp),
    
    // Additional shape definitions (not part of the Shapes class but useful for consistency)
    extraSmall = RoundedCornerShape(2.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

// Additional shapes for custom components
val ExtraSmallRoundedCornerShape = RoundedCornerShape(2.dp)
val ExtraLargeRoundedCornerShape = RoundedCornerShape(24.dp)

// Specific corner shapes
val TopRoundedCornerShape = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 16.dp,
    bottomStart = 0.dp,
    bottomEnd = 0.dp
)

val BottomRoundedCornerShape = RoundedCornerShape(
    topStart = 0.dp,
    topEnd = 0.dp,
    bottomStart = 16.dp,
    bottomEnd = 16.dp
)

// Left/Right rounded shapes
val LeftRoundedCornerShape = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 0.dp,
    bottomStart = 16.dp,
    bottomEnd = 0.dp
)

val RightRoundedCornerShape = RoundedCornerShape(
    topStart = 0.dp,
    topEnd = 16.dp,
    bottomStart = 0.dp,
    bottomEnd = 16.dp
)

// Button specific shapes
val PillShape = RoundedCornerShape(50)
val ButtonShape = RoundedCornerShape(12.dp)

// Card shapes
val CardShape = RoundedCornerShape(CardCornerRadius)
val SmallCardShape = RoundedCornerShape(8.dp)

// Cut corner shapes
val CutCornerCardShape = CutCornerShape(12.dp)

// Custom shape combinations
val TopLeftRoundedCornerShape = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 0.dp, 
    bottomStart = 0.dp,
    bottomEnd = 0.dp
) 