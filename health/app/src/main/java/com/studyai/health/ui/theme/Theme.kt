package com.studyai.health.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Light color scheme
private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = White,
    primaryContainer = PrimaryLight,
    onPrimaryContainer = DarkGreen,
    secondary = Secondary,
    onSecondary = White,
    secondaryContainer = SecondaryLight,
    onSecondaryContainer = DarkTeal,
    tertiary = Tertiary,
    onTertiary = White,
    background = Background,
    onBackground = TextPrimary,
    surface = Surface,
    onSurface = TextPrimary,
    error = Error,
    onError = White
)

// Dark color scheme
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = White,
    primaryContainer = DarkGreen,
    onPrimaryContainer = PrimaryLight,
    secondary = SecondaryDark,
    onSecondary = White,
    secondaryContainer = DarkTeal,
    onSecondaryContainer = SecondaryLight,
    tertiary = TertiaryDark,
    onTertiary = White,
    background = DarkBackground,
    onBackground = LightText,
    surface = DarkSurface,
    onSurface = LightText,
    error = ErrorDark,
    onError = White
)

// Custom dimension provider
val LocalAppDimens = staticCompositionLocalOf {
    AppDimensions()
}

// Class to hold custom dimensions
class AppDimensions {
    // Spacing
    val spaceExtraSmall = SpaceExtraSmall
    val spaceSmall = SpaceSmall
    val spaceMedium = SpaceMedium
    val spaceLarge = SpaceLarge
    val spaceExtraLarge = SpaceExtraLarge
    val spaceXXL = SpaceXXL
    val spaceXXXL = SpaceXXXL
    
    // Card dimensions
    val cardCornerRadius = CardCornerRadius
    val cardElevation = CardElevation
    val cardBorderWidth = CardBorderWidth
    
    // Button dimensions
    val buttonHeight = ButtonHeight
    val buttonCornerRadius = ButtonCornerRadius
    val buttonIconSize = ButtonIconSize
    
    // Input fields
    val inputFieldHeight = InputFieldHeight
    val inputCornerRadius = InputCornerRadius
    val inputPadding = InputPadding
    
    // Content padding
    val contentPaddingSmall = ContentPaddingSmall
    val contentPaddingMedium = ContentPaddingMedium
    val contentPaddingLarge = ContentPaddingLarge
    val screenPadding = ScreenPadding
}

/**
 * Theme for the application
 */
@Composable
fun StudyAIHealthTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    // Provide custom dimensions
    CompositionLocalProvider(
        LocalAppDimens provides AppDimensions()
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

// Extension properties to access dimensions easily
val MaterialTheme.dimens: AppDimensions
    @Composable
    get() = LocalAppDimens.current 