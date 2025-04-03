package com.studyai.health.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Warning

/**
 * Centralized object for managing app icons.
 * This helps maintain consistent icon usage throughout the app.
 */
object AppIcons {
    // Navigation
    val Back = Icons.Default.ArrowBack
    val BackRounded = Icons.Rounded.ArrowBack
    val Forward = Icons.Default.ArrowForward
    
    // Status
    val Check = Icons.Default.Check
    val CheckRounded = Icons.Rounded.Check
    val Warning = Icons.Default.Warning
    val WarningRounded = Icons.Rounded.Warning
    val Info = Icons.Default.Info
    val InfoRounded = Icons.Rounded.Info
    val InfoOutlined = Icons.Outlined.Info
    val Add = Icons.Default.Add
    
    // Navigation Icons - Filled versions
    val HomeFilled = Icons.Filled.Home
    val ProfileFilled = Icons.Filled.Person
    val SettingsFilled = Icons.Filled.Settings
    
    // Navigation Icons - Outlined versions
    val HomeOutlined = Icons.Outlined.Home
    val ProfileOutlined = Icons.Outlined.Person
    val SettingsOutlined = Icons.Outlined.Settings
    
    // Navigation Icons - Rounded versions
    val HomeRounded = Icons.Rounded.Home
    val ProfileRounded = Icons.Rounded.Person
    val SettingsRounded = Icons.Rounded.Settings
    
    // Health
    val Heart = Icons.Default.Favorite
    val HeartOutlined = Icons.Outlined.Favorite
    val HeartBorder = Icons.Default.FavoriteBorder
    val HeartBorderOutlined = Icons.Outlined.FavoriteBorder
    val HeartRounded = Icons.Rounded.Favorite
    
    // Additional icons
    val Person = Icons.Default.Person
    val PersonOutlined = Icons.Outlined.Person
    val ThumbUp = Icons.Default.ThumbUp
    val Star = Icons.Default.Star
    val Calendar = Icons.Default.DateRange
    val CalendarOutlined = Icons.Outlined.DateRange
    val Account = Icons.Default.AccountCircle
    val AccountOutlined = Icons.Outlined.AccountCircle
    val Location = Icons.Default.LocationOn
    
    // Aliases for common uses
    val FavoriteFilled = Heart
    val FavoriteOutlined = HeartOutlined
} 