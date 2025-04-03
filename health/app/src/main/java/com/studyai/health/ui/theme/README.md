# UI Theme Documentation

This directory contains the theme definition for the StudyAI Health application, using Material 3 theming system. The theme components provide a consistent visual language throughout the application.

## Structure

The theme is organized into the following files:

### 1. Color.kt

Defines all color values used in the application:
- Primary, secondary, and tertiary colors
- Background and surface colors
- Text colors
- Status colors (success, error, warning)
- Health-specific colors (heart rate, sleep, etc.)
- Chart colors

### 2. Type.kt

Defines typography styles for the application:
- Display styles (large, medium, small)
- Headline styles
- Title styles
- Body styles
- Label styles

### 3. Shape.kt

Defines shape styles for UI components:
- Standard rounded corner shapes (small, medium, large)
- Custom shapes (pill shapes, cut corners)
- Directional shapes (top rounded, bottom rounded)

### 4. Dimensions.kt

Defines standardized dimensions for spacing and sizing:
- Spacing values (small, medium, large)
- Elevation values
- Component sizes (buttons, cards, icons)
- Content padding values
- Font sizes

### 5. Theme.kt

Combines all theme elements into a cohesive theme:
- Defines color schemes for light and dark modes
- Creates the StudyAIHealthTheme composable
- Sets up system UI colors (status bar)
- Provides dimension values through CompositionLocal

### 6. Icons.kt

Centralizes icon usage across the application:
- Basic UI icons
- Navigation icons
- Utility icons
- Health-specific icons

## Usage

Apply the theme by wrapping your composables in the `StudyAIHealthTheme`:

```kotlin
@Composable
fun MyApp() {
    StudyAIHealthTheme {
        // Your app content here
        Surface(color = MaterialTheme.colorScheme.background) {
            // ...
        }
    }
}
```

Access theme values in composables:

```kotlin
@Composable
fun MyComponent() {
    val primaryColor = MaterialTheme.colorScheme.primary
    val headlineStyle = MaterialTheme.typography.headlineMedium
    val roundedShape = MaterialTheme.shapes.medium
    val standardPadding = MaterialTheme.dimens.contentPaddingMedium
    
    // Use these values in your UI elements
}
```

## Components Created

The following reusable components have been created using this theme:

### Buttons
- PrimaryButton - Main call-to-action button
- SecondaryButton - Secondary actions button
- PrimaryIconButton - Icon button with filled background
- SecondaryIconButton - Icon button with outline
- FloatingIconButton - Floating action button style

### Cards
- MetricCard - For displaying health metrics with values
- StatusCard - For showing health status with progress
- SummaryCard - For summarizing multiple data points

## Design Principles

1. **Consistency**: Use the predefined theme values rather than hardcoded values
2. **Accessibility**: Ensure proper contrast ratios and readable text
3. **Adaptability**: Support both light and dark themes
4. **Scalability**: Responsive layouts that adapt to different screen sizes
5. **Hierarchy**: Clear visual hierarchy to guide users through the interface

## Dynamic Theming

The application supports dynamic color theming on Android 12+ using Material You. This can be disabled by setting `dynamicColor = false` in the `StudyAIHealthTheme` function. 